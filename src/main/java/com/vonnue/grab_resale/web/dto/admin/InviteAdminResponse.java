package com.vonnue.grab_resale.web.dto.admin;

import java.time.Instant;

import com.vonnue.grab_resale.persistence.entity.User;

public record InviteAdminResponse(
        Long userId,
        String email,
        String name,
        String inviteToken,
        Instant inviteTokenExpiry
) {

    public static InviteAdminResponse from(User user) {
        return new InviteAdminResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getInviteToken(),
                user.getInviteTokenExpiry()
        );
    }
}
