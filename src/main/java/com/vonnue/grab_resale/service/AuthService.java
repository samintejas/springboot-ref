package com.vonnue.grab_resale.service;

import com.vonnue.grab_resale.web.dto.auth.AuthResult;
import com.vonnue.grab_resale.web.dto.auth.LoginRequest;
import com.vonnue.grab_resale.web.dto.auth.RegisterRequest;
import com.vonnue.grab_resale.web.dto.auth.SetPasswordRequest;
import com.vonnue.grab_resale.web.dto.auth.UserResponse;

public interface AuthService {

    AuthResult register(RegisterRequest request);

    AuthResult login(LoginRequest request);

    String refreshAccessToken(String refreshToken);

    UserResponse getCurrentUser(String email);

    UserResponse setPassword(SetPasswordRequest request);
}
