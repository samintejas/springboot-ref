package com.vonnue.grab_resale.web.dto.listing;

import com.vonnue.grab_resale.persistence.embeddable.OwnershipInfo;

public record OwnershipInfoResponse(
        String registeredOwner,
        String contactNumber,
        String email,
        String address,
        Boolean isSeller
) {

    public static OwnershipInfoResponse from(OwnershipInfo info) {
        if (info == null) return null;
        return new OwnershipInfoResponse(
                info.getRegisteredOwner(),
                info.getContactNumber(),
                info.getEmail(),
                info.getAddress(),
                info.getSeller()
        );
    }
}
