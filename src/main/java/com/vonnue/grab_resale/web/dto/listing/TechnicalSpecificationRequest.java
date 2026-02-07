package com.vonnue.grab_resale.web.dto.listing;

import com.vonnue.grab_resale.common.constants.FuelType;
import com.vonnue.grab_resale.common.constants.Transmission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TechnicalSpecificationRequest(
        @NotBlank String engineNumber,
        @NotBlank String chassisNumber,
        @NotNull FuelType fuelType,
        @NotNull Transmission transmission,
        @Positive Integer engineCapacity,
        @Positive Integer horsePower
) {
}
