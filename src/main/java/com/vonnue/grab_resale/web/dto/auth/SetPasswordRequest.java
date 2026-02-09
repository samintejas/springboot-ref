package com.vonnue.grab_resale.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SetPasswordRequest(
        @NotBlank String token,
        @NotBlank @Size(min = 8, max = 128) String password
) {
}
