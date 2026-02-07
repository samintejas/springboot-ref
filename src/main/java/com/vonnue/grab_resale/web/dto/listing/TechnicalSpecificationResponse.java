package com.vonnue.grab_resale.web.dto.listing;

import com.vonnue.grab_resale.common.constants.FuelType;
import com.vonnue.grab_resale.persistence.embeddable.TechnicalSpecification;
import com.vonnue.grab_resale.common.constants.Transmission;

public record TechnicalSpecificationResponse(
        String engineNumber,
        String chassisNumber,
        FuelType fuelType,
        Transmission transmission,
        Integer engineCapacity,
        Integer horsePower
) {

    public static TechnicalSpecificationResponse from(TechnicalSpecification spec) {
        if (spec == null) return null;
        return new TechnicalSpecificationResponse(
                spec.getEngineNumber(),
                spec.getChassisNumber(),
                spec.getFuelType(),
                spec.getTransmission(),
                spec.getEngineCapacity(),
                spec.getHorsePower()
        );
    }
}
