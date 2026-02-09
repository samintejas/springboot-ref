package com.vonnue.grab_resale.web.controller;

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
import com.vonnue.grab_resale.service.CookieService;
import com.vonnue.grab_resale.web.dto.auth.AuthResult;
import com.vonnue.grab_resale.web.dto.auth.LoginRequest;
import com.vonnue.grab_resale.web.dto.auth.RegisterRequest;
import com.vonnue.grab_resale.web.dto.auth.SetPasswordRequest;
import com.vonnue.grab_resale.web.dto.auth.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Registration, login, token refresh, and password management")
public class AuthController {

    private final AuthService authService;
    private final CookieService cookieService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "Creates a new user account and sets auth cookies")
    public UserResponse register(@Valid @RequestBody RegisterRequest request,
                                 HttpServletResponse response) {
        AuthResult result = authService.register(request);
        setAuthCookies(response, result);
        return result.user();
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates user credentials and sets auth cookies")
    public UserResponse login(@Valid @RequestBody LoginRequest request,
                              HttpServletResponse response) {
        AuthResult result = authService.login(request);
        setAuthCookies(response, result);
        return result.user();
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Logout", description = "Clears auth cookies")
    public void logout(HttpServletResponse response) {
        cookieService.clearCookies(response);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Refresh access token", description = "Uses refresh token cookie to issue a new access token")
    public void refresh(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response) {
        String accessToken = authService.refreshAccessToken(refreshToken);
        cookieService.addAccessTokenCookie(response, accessToken);
    }

    @PostMapping("/set-password")
    @Operation(summary = "Set password", description = "Sets password for an invited admin using a set-password token")
    public UserResponse setPassword(@Valid @RequestBody SetPasswordRequest request) {
        return authService.setPassword(request);
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns the authenticated user's profile")
    public UserResponse me(Principal principal) {
        return authService.getCurrentUser(principal.getName());
    }

    private void setAuthCookies(HttpServletResponse response, AuthResult result) {
        cookieService.addAccessTokenCookie(response, result.accessToken());
        cookieService.addRefreshTokenCookie(response, result.refreshToken());
    }
}