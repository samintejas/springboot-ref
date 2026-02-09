package com.vonnue.grab_resale.web.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InviteAdminRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(max = 255) String name
) {
}
