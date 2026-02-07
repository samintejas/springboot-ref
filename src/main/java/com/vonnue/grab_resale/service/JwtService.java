package com.vonnue.grab_resale.service;

import com.vonnue.grab_resale.persistence.entity.User;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractEmail(String token);

    String extractRole(String token);

    String extractName(String token);

    boolean isTokenValid(String token);
}
