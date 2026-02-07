package com.vonnue.grab_resale.web.dto.listing;

import java.time.LocalDate;

import com.vonnue.grab_resale.persistence.embeddable.RegistrationOwnership;

public record RegistrationOwnershipResponse(
        String ownerName,
        LocalDate registrationDate,
        Integer numberOfTransfers,
        String contactNumber,
        String emailAddress,
        String address,
        FileAttachmentResponse logCard
) {

    public static RegistrationOwnershipResponse from(RegistrationOwnership reg) {
        if (reg == null) return null;
        return new RegistrationOwnershipResponse(
                reg.getOwnerName(),
                reg.getRegistrationDate(),
                reg.getNumberOfTransfers(),
                reg.getContactNumber(),
                reg.getEmailAddress(),
                reg.getAddress(),
                FileAttachmentResponse.from(reg.getLogCard())
        );
    }
}
