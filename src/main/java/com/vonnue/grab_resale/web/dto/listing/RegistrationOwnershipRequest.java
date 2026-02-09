package com.vonnue.grab_resale.web.dto.listing;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

public record RegistrationOwnershipRequest(
        String ownerName,
        LocalDate registrationDate,
        @PositiveOrZero Integer numberOfTransfers,
        String contactNumber,
        String emailAddress,
        String address,
        @Valid FileAttachmentRequest logCard
) {
}
