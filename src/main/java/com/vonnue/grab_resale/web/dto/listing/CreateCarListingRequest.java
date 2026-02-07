package com.vonnue.grab_resale.web.dto.listing;

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateCarListingRequest(
        @Valid @NotNull CarDetailsRequest carDetails,
        @Valid @NotNull SellerDetailsRequest sellerDetails,
        Map<String, String> otherDetails
) {
}
