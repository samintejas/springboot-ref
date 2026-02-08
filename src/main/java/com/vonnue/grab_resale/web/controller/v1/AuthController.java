package com.vonnue.grab_resale.web.controller.v1;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vonnue.grab_resale.service.AuthService;
import com.vonnue.grab_resale.web.dto.ApiResponse;
import com.vonnue.grab_resale.web.dto.auth.LoginRequest;
import com.vonnue.grab_resale.web.dto.auth.RegisterRequest;
import com.vonnue.grab_resale.web.dto.auth.SetPasswordRequest;
import com.vonnue.grab_resale.web.dto.auth.UserResponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest request,
                                              HttpServletResponse response) {
        return ApiResponse.of(authService.register(request, response));
    }

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@Valid @RequestBody LoginRequest request,
                                           HttpServletResponse response) {
        return ApiResponse.of(authService.login(request, response));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ApiResponse.withMessage("Logged out successfully");
    }

    @PostMapping("/refresh")
    public ApiResponse<Void> refresh(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response) {
        authService.refreshAccessToken(refreshToken, response);
        return ApiResponse.withMessage("Token refreshed successfully");
    }

    @PostMapping("/set-password")
    public ApiResponse<UserResponse> setPassword(@Valid @RequestBody SetPasswordRequest request) {
        return ApiResponse.of(authService.setPassword(request));
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> me(Principal principal) {
        return ApiResponse.of(authService.getCurrentUser(principal.getName()));
    }
}
