package com.vonnue.grab_resale.service.impl;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.vonnue.grab_resale.config.JwtProperties;
import com.vonnue.grab_resale.persistence.entity.User;
import com.vonnue.grab_resale.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtServiceImpl(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes());
    }

    @Override
    public String generateAccessToken(User user) {
        return buildToken(user, jwtProperties.accessTokenExpiration().toMillis());
    }

    @Override
    public String generateRefreshToken(User user) {
        return buildToken(user, jwtProperties.refreshTokenExpiration().toMillis());
    }

    @Override
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    @Override
    public String extractName(String token) {
        return extractClaims(token).get("name", String.class);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    private String buildToken(User user, long expirationMs) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .claim("name", user.getName())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(signingKey)
                .compact();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
