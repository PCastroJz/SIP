package com.beto.sip.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beto.sip.domain.user.User;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key key;
    private final long expirationMs;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(User user, List<String> roles, List<String> permissions) {
        return Jwts.builder()
                .setSubject(user.getUsername().getValue())
                .claim("userId", user.getId().getValue())
                .claim("email", user.getEmail().getValue())
                .claim("status", user.getStatus())
                .claim("roles", roles)
                .claim("permissions", permissions)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> getAllClaims(String token) {
        return parseClaims(token);
    }

    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return parseClaims(token).get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}