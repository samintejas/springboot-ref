package com.vonnue.grab_resale.web.dto.listing;

import com.vonnue.grab_resale.common.constants.SellerType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SellerDetailsRequest(
        @NotNull SellerType sellerType,
        @Valid @NotNull OwnershipInfoRequest ownershipInfo,
        @Valid SellerInfoRequest sellerInfo
) {
}
