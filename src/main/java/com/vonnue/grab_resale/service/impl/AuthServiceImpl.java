package com.vonnue.grab_resale.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vonnue.grab_resale.common.constants.Role;
import com.vonnue.grab_resale.exception.BadRequestException;
import com.vonnue.grab_resale.exception.ResourceNotFoundException;
import com.vonnue.grab_resale.persistence.entity.User;
import com.vonnue.grab_resale.persistence.repository.UserRepository;
import com.vonnue.grab_resale.service.AuthService;
import com.vonnue.grab_resale.service.JwtService;
import com.vonnue.grab_resale.web.dto.auth.AuthResult;
import com.vonnue.grab_resale.web.dto.auth.LoginRequest;
import com.vonnue.grab_resale.web.dto.auth.RegisterRequest;
import com.vonnue.grab_resale.web.dto.auth.SetPasswordRequest;
import com.vonnue.grab_resale.web.dto.auth.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final String dummyPasswordHash = "$2a$10$abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUV";

    @Override
    @Transactional
    public AuthResult register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Registration failed");
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name(),
                Role.USER
        );
        user = userRepository.save(user);

        return buildAuthResult(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResult login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email());

        // Always run password comparison to prevent timing oracle
        String storedHash = userOpt
                .map(User::getPassword)
                .orElse(dummyPasswordHash);

        boolean passwordMatches = storedHash != null
                && passwordEncoder.matches(request.password(), storedHash);

        if (userOpt.isEmpty() || !passwordMatches) {
            throw new BadRequestException("Invalid email or password");
        }

        return buildAuthResult(userOpt.get());
    }

    @Override
    @Transactional(readOnly = true)
    public String refreshAccessToken(String refreshToken) {
        if (refreshToken == null || !jwtService.isTokenValid(refreshToken)) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        String tokenType = jwtService.extractTokenType(refreshToken);
        if (!JwtServiceImpl.TOKEN_TYPE_REFRESH.equals(tokenType)) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid or expired refresh token"));

        return jwtService.generateAccessToken(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public UserResponse setPassword(SetPasswordRequest request) {
        User user = userRepository.findByInviteToken(request.token())
                .orElseThrow(() -> new BadRequestException("Invalid invite token"));

        if (!user.isInviteTokenValid()) {
            throw new BadRequestException("Invite token has expired");
        }

        user.setPassword(passwordEncoder.encode(request.password()));
        user.setInviteToken(null);
        user.setInviteTokenExpiry(null);
        user = userRepository.save(user);

        return UserResponse.from(user);
    }

    private AuthResult buildAuthResult(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthResult(UserResponse.from(user), accessToken, refreshToken);
    }
}
