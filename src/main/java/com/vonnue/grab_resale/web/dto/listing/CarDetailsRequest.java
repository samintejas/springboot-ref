package com.vonnue.grab_resale.web.dto.listing;

import java.time.LocalDate;

import com.vonnue.grab_resale.common.constants.CarCondition;
import com.vonnue.grab_resale.common.constants.CarType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CarDetailsRequest(
        @NotNull CarCondition carCondition,
        @NotNull Long makeId,
        @NotNull Long modelId,
        @NotNull LocalDate yearOfManufacturing,
        @NotBlank String plateNumber,
        @Positive Integer mileage,
        CarType carType,
        String color,
        @Valid @NotNull TechnicalSpecificationRequest technicalSpecification,
        @Valid RegistrationOwnershipRequest registrationOwnership,
        @Valid PriceCoeDetailsRequest priceCoeDetails
) {
}
