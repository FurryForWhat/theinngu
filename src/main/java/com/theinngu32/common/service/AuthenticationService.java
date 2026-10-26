package com.theinngu32.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theinngu32.common.dto.auth.AuthenticationRequest;
import com.theinngu32.common.dto.auth.AuthenticationResponse;
import com.theinngu32.common.dto.auth.ChangePasswordRequest;
import com.theinngu32.common.dto.auth.ResetPasswordRequest;
import com.theinngu32.common.dto.user.UserCreateDTO;
import com.theinngu32.common.entity.Token;
import com.theinngu32.common.entity.TokenType;
import com.theinngu32.common.exception.AccountNotConfirmedException;
import com.theinngu32.common.exception.AuthResponseException;
import com.theinngu32.common.exception.ResourceAlreadyExistsException;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.repository.TokenRepository;
import com.theinngu32.common.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  @Value("${environment}")
  private String environment;
  @Value("${application.security.jwt.expiration}")
  private int jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private int refreshExpiration;

  public AuthenticationResponse register(UserCreateDTO request) {
    var savedUser = userService.createUser(request);
    var jwtToken = jwtService.generateToken(savedUser.getEmail());
    var refreshToken = jwtService.generateRefreshToken(savedUser.getEmail());
    saveUserToken(savedUser.getId(), jwtToken);
    return new AuthenticationResponse(jwtToken, refreshToken);
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
            )
    );
    var user = repository.findByEmail(request.email())
            .orElseThrow(() -> new ResourceNotFoundException("User with %s not found".formatted(request.email())));
    if(Objects.isNull(user.getConfirmedAt())){
      throw new AccountNotConfirmedException("Your account is not confirmed. Please check your email.");
    }
    var jwtToken = jwtService.generateToken(user.getEmail());
    var refreshToken = jwtService.generateRefreshToken(user.getEmail());
    revokeAllUserTokens(user.getId());
    saveUserToken(user.getId(), jwtToken);
    Cookie accessTokenCookie = new Cookie("access_token", jwtToken);
    accessTokenCookie.setHttpOnly(true);
    accessTokenCookie.setPath("/");
    accessTokenCookie.setSecure(!environment.equalsIgnoreCase("local"));
    accessTokenCookie.setMaxAge(jwtExpiration);
    Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setSecure(!environment.equalsIgnoreCase("local"));
    refreshTokenCookie.setMaxAge(refreshExpiration);
    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);
    return new AuthenticationResponse(jwtToken, refreshToken);
  }

  private void saveUserToken(Long userId, String jwtToken) {
    var token = Token.builder()
            .userId(userId)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Long userId) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(userId);
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user.getEmail())) {
        var accessToken = jwtService.generateToken(user.getEmail());
        revokeAllUserTokens(user.getId());
        saveUserToken(user.getId(), accessToken);
        var authResponse = new AuthenticationResponse(accessToken, refreshToken);
        response.setContentType("application/json");
        try {
          new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        } catch (IOException e) {
          log.error("Failed to write authResponse to response stream", e);
          throw new AuthResponseException("Error writing authentication response to output stream.", e);
        }
      }
    }
  }

  public Cookie generateNewAccessToken(String refreshToken) {
    final String userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user.getEmail())) {
        var newAccessToken = jwtService.generateToken(user.getEmail());
        revokeAllUserTokens(user.getId());
        saveUserToken(user.getId(), newAccessToken);
        Cookie accessTokenCookie = new Cookie("access_token", newAccessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(!environment.equalsIgnoreCase("local"));
        accessTokenCookie.setMaxAge(jwtExpiration);
        return accessTokenCookie;
      }
    }
    return null;
  }
}
