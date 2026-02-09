package com.vonnue.grab_resale.web.dto.listing;

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCarListingRequest(
        @Valid @NotNull CarDetailsRequest carDetails,
        @Valid @NotNull SellerDetailsRequest sellerDetails,
        @Size(max = 50) Map<String, String> otherDetails
) {
}
