package com.theinngu32.common.config;

import com.theinngu32.common.auditing.ApplicationAuditAware;
import com.theinngu32.common.dto.MyUserDetails;
import com.theinngu32.common.repository.TokenRepository;
import com.theinngu32.common.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    @Value("${environment}")
    private String environment;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new ApplicationAuditAware();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> new MyUserDetails(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(email))));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        final int PASSWORD_STRENGTH = 12;
        return new BCryptPasswordEncoder(PASSWORD_STRENGTH);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            Cookie[] cookies = request.getCookies();
            final String accessToken = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("access_token"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
            final String refreshToken = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("refresh_token"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

            if (refreshToken == null) {
                return;
            }

            if (accessToken == null) {
                return;
            }

            tokenRepository.findByToken(accessToken).ifPresent(token -> {
                token.setExpired(true);
                token.setRevoked(true);
                tokenRepository.save(token);
                SecurityContextHolder.clearContext();
            });

            // clear all cookies
            Cookie accessTokenCookie = new Cookie("access_token", null);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setSecure(!environment.equalsIgnoreCase("local"));
            accessTokenCookie.setMaxAge(0);
            Cookie refreshTokenCookie = new Cookie("refresh_token", null);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setSecure(!environment.equalsIgnoreCase("local"));
            refreshTokenCookie.setMaxAge(0);
        };
    }
}
