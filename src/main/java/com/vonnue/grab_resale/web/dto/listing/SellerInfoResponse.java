package com.vonnue.grab_resale.web.dto.listing;

import com.vonnue.grab_resale.persistence.embeddable.SellerInfo;

public record SellerInfoResponse(
        String name,
        String contactNumber,
        String whatsAppNumber,
        String companyAddress,
        String email,
        FileAttachmentResponse letterOfAuthorization
) {

    public static SellerInfoResponse from(SellerInfo info) {
        if (info == null) return null;
        return new SellerInfoResponse(
                info.getName(),
                info.getContactNumber(),
                info.getWhatsAppNumber(),
                info.getCompanyAddress(),
                info.getEmail(),
                FileAttachmentResponse.from(info.getLetterOfAuthorization())
        );
    }
}
