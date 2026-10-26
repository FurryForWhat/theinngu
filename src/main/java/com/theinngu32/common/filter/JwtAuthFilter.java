package com.theinngu32.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theinngu32.common.dto.auth.AuthenticationResponse;
import com.theinngu32.common.exception.AuthResponseException;
import com.theinngu32.common.repository.TokenRepository;
import com.theinngu32.common.service.AuthenticationService;
import com.theinngu32.common.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.theinngu32.common.config.SecurityConfig.WHITE_LIST_URL;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("Processing request: {}", request.getRequestURI());

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            log.debug("No cookies found in the request"); // Log missing cookies
            filterChain.doFilter(request, response);
            return;
        }

         String accessToken = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("access_token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        final String refreshToken = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("refresh_token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if(refreshToken == null){
            log.debug("No Refresh token found in the request"); // Log missing JWT
            filterChain.doFilter(request, response);
            return;
        }

        if(accessToken == null){
            log.debug("Generating new access token from refresh token");
            final Cookie newAccessToken = authenticationService.generateNewAccessToken(refreshToken);
            response.addCookie(newAccessToken);
            accessToken = newAccessToken.getValue();
        }

        final String userEmail = jwtService.extractUsername(accessToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenValid = tokenRepository.findByToken(accessToken)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(accessToken, userEmail) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(WHITE_LIST_URL)
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

}
