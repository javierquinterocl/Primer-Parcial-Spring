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
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {

            if (!jwtUtil.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }


            Optional<TokenModel> tokenOpt = tokenRepository.findByToken(token);
            if (tokenOpt.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            TokenModel stored = tokenOpt.get();
            if (stored.isRevoked() || stored.isExpired()) {
                filterChain.doFilter(request, response);
                return;
            }


            String email = jwtUtil.getEmailFromToken(token);
            Optional<UserModel> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            UserModel user = userOpt.get();


            List<SimpleGrantedAuthority> authorities = List.of();
            if (user.getRole() != null && !user.getRole().isBlank()) {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception ex) {
            log.warn("JWT autenticacion fallida: {}", ex.getMessage());

        }

        filterChain.doFilter(request, response);
    }
}

