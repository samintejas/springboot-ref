package com.vonnue.grab_resale.web.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InviteAdminRequest(
        @NotBlank @Email String email,
        @NotBlank String name
) {
}
