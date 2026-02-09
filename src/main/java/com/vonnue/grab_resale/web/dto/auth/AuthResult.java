package com.vonnue.grab_resale.web.dto.auth;

public record AuthResult(
        UserResponse user,
        String accessToken,
        String refreshToken
) {
}
