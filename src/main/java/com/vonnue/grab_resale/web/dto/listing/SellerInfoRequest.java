package com.vonnue.grab_resale.web.dto.listing;

import jakarta.validation.Valid;

public record SellerInfoRequest(
        String name,
        String contactNumber,
        String whatsAppNumber,
        String companyAddress,
        String email,
        @Valid FileAttachmentRequest letterOfAuthorization
) {
}
