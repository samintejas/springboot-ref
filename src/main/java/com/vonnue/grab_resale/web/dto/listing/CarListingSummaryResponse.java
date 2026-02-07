package com.vonnue.grab_resale.web.dto.listing;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import com.vonnue.grab_resale.common.constants.CarCondition;
import com.vonnue.grab_resale.persistence.entity.CarListing;

public record CarListingSummaryResponse(
        Long id,
        CarCondition carCondition,
        String makeName,
        String modelName,
        LocalDate yearOfManufacturing,
        Integer mileage,
        BigDecimal askingPrice,
        String primaryImageUrl,
        Instant createdAt
) {

    public static CarListingSummaryResponse from(CarListing listing, String primaryImageUrl) {
        return new CarListingSummaryResponse(
                listing.getId(),
                listing.getCarCondition(),
                listing.getMake().getName(),
                listing.getModel().getName(),
                listing.getYearOfManufacturing(),
                listing.getMileage(),
                listing.getPriceCoeDetails() != null ? listing.getPriceCoeDetails().getAskingPrice() : null,
                primaryImageUrl,
                listing.getCreatedAt()
        );
    }
}
