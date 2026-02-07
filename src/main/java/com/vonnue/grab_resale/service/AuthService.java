package com.vonnue.grab_resale.service;

import com.vonnue.grab_resale.web.dto.auth.LoginRequest;
import com.vonnue.grab_resale.web.dto.auth.RegisterRequest;
import com.vonnue.grab_resale.web.dto.auth.UserResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request, HttpServletResponse response);

    UserResponse login(LoginRequest request, HttpServletResponse response);

    void logout(HttpServletResponse response);

    void refreshAccessToken(String refreshToken, HttpServletResponse response);

    UserResponse getCurrentUser(String email);
}
