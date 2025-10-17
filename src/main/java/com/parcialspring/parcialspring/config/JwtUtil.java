package com.parcialspring.parcialspring.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.parcialspring.parcialspring.model.UserModel;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    private final String secret;
    private final long expirationMs;
    private final long refreshExpirationMs;
    private final Key signingKey;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expirationMs}") long expirationMs,
                   @Value("${jwt.refreshExpirationMs}") long refreshExpirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
        this.signingKey = deriveKeyFromSecret(secret);
    }

    private Key deriveKeyFromSecret(String secret) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to derive signing key from secret", ex);
        }
    }

    public String generateToken(final UserModel user) {
        return buildToken(user, expirationMs);
    }

    public String generateRefreshToken(final UserModel user) {
        return buildToken(user, refreshExpirationMs);
    }

    private String buildToken(final UserModel user, final long expiration) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setId(user.getId() != null ? user.getId().toString() : null)
                .setSubject(user.getEmail())
                .claim("name", user.getFirstName() + " " + user.getLastName())
                .claim("code", user.getCode())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(signingKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    public long getExpirationMs() {
        return expirationMs;
    }
}
