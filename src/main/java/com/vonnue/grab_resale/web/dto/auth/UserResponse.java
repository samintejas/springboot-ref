package com.vonnue.grab_resale.web.dto.auth;

import com.vonnue.grab_resale.common.constants.Role;
import com.vonnue.grab_resale.persistence.entity.User;

public record UserResponse(
        Long id,
        String email,
        String name,
        Role role
) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getRole());
    }
}
