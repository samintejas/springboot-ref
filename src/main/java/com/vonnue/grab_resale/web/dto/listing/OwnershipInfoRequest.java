package com.vonnue.grab_resale.web.dto.listing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OwnershipInfoRequest(
        @NotBlank String registeredOwner,
        @NotBlank String contactNumber,
        String email,
        String address,
        @NotNull Boolean isSeller
) {
}
