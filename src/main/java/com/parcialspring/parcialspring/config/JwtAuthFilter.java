package com.parcialspring.parcialspring.config;

import com.parcialspring.parcialspring.model.TokenModel;
import com.parcialspring.parcialspring.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        log.info("Authorization header: {}", authHeader != null ? "Present" : "Missing");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn(" No bearer token found");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        log.info(" Token extracted: {}...", token.substring(0, Math.min(20, token.length())));

        try {
            if (!jwtUtil.validateToken(token)) {
                log.warn(" Token validation failed");
                filterChain.doFilter(request, response);
                return;
            }
            log.info(" Token validated successfully");

            // Validación opcional en BD - no bloquea si no está
            Optional<TokenModel> tokenOpt = tokenRepository.findByToken(token);
            if (tokenOpt.isPresent()) {
                TokenModel stored = tokenOpt.get();

                // Solo verificar si está explícitamente revocado
                if (stored.isRevoked()) {
                    log.warn(" Token is revoked");
                    filterChain.doFilter(request, response);
                    return;
                }
                log.info(" Token found in database and not revoked");
            } else {
                log.warn(" Token not found in database, but allowing based on JWT signature");
            }

            String email = jwtUtil.getEmailFromToken(token);
            log.info("Email from token: {}", email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, List.of());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(" Authentication set successfully for {}", email);

        } catch (Exception ex) {
            log.error("JWT authentication failed with exception: {}", ex.getMessage(), ex);
        }

        filterChain.doFilter(request, response);
    }
}
