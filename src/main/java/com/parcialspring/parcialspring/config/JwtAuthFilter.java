package com.parcialspring.parcialspring.config;

import com.parcialspring.parcialspring.model.UserModel;
import com.parcialspring.parcialspring.model.TokenModel;
import com.parcialspring.parcialspring.repository.TokenRepository;
import com.parcialspring.parcialspring.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.info("🔍 Request: {} {}", request.getMethod(), request.getRequestURI());
        log.info("🔑 Authorization header: {}", authHeader != null ? "Present" : "Missing");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("❌ No bearer token found");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        log.info("📝 Token extracted: {}...", token.substring(0, Math.min(20, token.length())));

        try {
            if (!jwtUtil.validateToken(token)) {
                log.warn("❌ Token validation failed");
                filterChain.doFilter(request, response);
                return;
            }
            log.info("✅ Token validated successfully");

            Optional<TokenModel> tokenOpt = tokenRepository.findByToken(token);
            if (tokenOpt.isEmpty()) {
                log.warn("❌ Token not found in database");
                filterChain.doFilter(request, response);
                return;
            }
            log.info("✅ Token found in database");

            TokenModel stored = tokenOpt.get();

            if (stored.getExpiresAt() != null && stored.getExpiresAt().isBefore(LocalDateTime.now())) {
                log.warn("❌ Token expired in database: {}", stored.getExpiresAt());
                if (!stored.isExpired()) {
                    stored.setExpired(true);
                    tokenRepository.save(stored);
                }
                filterChain.doFilter(request, response);
                return;
            }

            if (stored.isRevoked() || stored.isExpired()) {
                log.warn("❌ Token revoked or expired flag set");
                filterChain.doFilter(request, response);
                return;
            }
            log.info("✅ Token is active and not revoked");

            String email = jwtUtil.getEmailFromToken(token);
            log.info("📧 Email from token: {}", email);

            Optional<UserModel> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                log.warn("❌ User not found: {}", email);
                filterChain.doFilter(request, response);
                return;
            }
            UserModel user = userOpt.get();
            log.info("✅ User found: {} with role: {}", user.getEmail(), user.getRole());

            List<SimpleGrantedAuthority> authorities = List.of();
            if (user.getRole() != null && !user.getRole().isBlank()) {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("✅✅✅ Authentication set successfully for {}", user.getEmail());

        } catch (Exception ex) {
            log.error("❌❌❌ JWT authentication failed with exception: {}", ex.getMessage(), ex);
        }

        filterChain.doFilter(request, response);
    }
}

