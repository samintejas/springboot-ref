package com.vonnue.grab_resale.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vonnue.grab_resale.common.constants.Role;
import com.vonnue.grab_resale.persistence.entity.User;
import com.vonnue.grab_resale.exception.BadRequestException;
import com.vonnue.grab_resale.exception.ResourceNotFoundException;
import com.vonnue.grab_resale.persistence.repository.UserRepository;
import com.vonnue.grab_resale.service.AuthService;
import com.vonnue.grab_resale.service.CookieService;
import com.vonnue.grab_resale.service.JwtService;
import com.vonnue.grab_resale.web.dto.auth.LoginRequest;
import com.vonnue.grab_resale.web.dto.auth.RegisterRequest;
import com.vonnue.grab_resale.web.dto.auth.UserResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CookieService cookieService;

    @Override
    public UserResponse register(RegisterRequest request, HttpServletResponse response) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name(),
                Role.USER
        );
        user = userRepository.save(user);

        setAuthCookies(user, response);
        return UserResponse.from(user);
    }

    @Override
    public UserResponse login(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.email())
                .filter(u -> passwordEncoder.matches(request.password(), u.getPassword()))
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        setAuthCookies(user, response);
        return UserResponse.from(user);
    }

    @Override
    public void logout(HttpServletResponse response) {
        cookieService.clearCookies(response);
    }

    @Override
    public void refreshAccessToken(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || !jwtService.isTokenValid(refreshToken)) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid or expired refresh token"));

        String accessToken = jwtService.generateAccessToken(user);
        cookieService.addAccessTokenCookie(response, accessToken);
    }

    @Override
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
        return UserResponse.from(user);
    }

    private void setAuthCookies(User user, HttpServletResponse response) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        cookieService.addAccessTokenCookie(response, accessToken);
        cookieService.addRefreshTokenCookie(response, refreshToken);
    }
}
