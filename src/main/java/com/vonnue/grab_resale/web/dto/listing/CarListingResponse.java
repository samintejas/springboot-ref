package com.vonnue.grab_resale.web.dto.listing;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vonnue.grab_resale.common.constants.CarCondition;
import com.vonnue.grab_resale.persistence.entity.CarImage;
import com.vonnue.grab_resale.persistence.entity.CarListing;
import com.vonnue.grab_resale.common.constants.CarType;
import com.vonnue.grab_resale.persistence.entity.ListingAttribute;
import com.vonnue.grab_resale.common.constants.SellerType;

public record CarListingResponse(
        Long id,
        CarCondition carCondition,
        Long makeId,
        String makeName,
        Long modelId,
        String modelName,
        SellerType sellerType,
        LocalDate yearOfManufacturing,
        String plateNumber,
        Integer mileage,
        CarType carType,
        String color,
        TechnicalSpecificationResponse technicalSpecification,
        RegistrationOwnershipResponse registrationOwnership,
        PriceCoeDetailsResponse priceCoeDetails,
        OwnershipInfoResponse ownershipInfo,
        SellerInfoResponse sellerInfo,
        List<CarImageResponse> images,
        Map<String, String> attributes,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy
) {

    public static CarListingResponse from(CarListing listing, List<CarImage> images, List<ListingAttribute> attributes) {
        return new CarListingResponse(
                listing.getId(),
                listing.getCarCondition(),
                listing.getMake().getId(),
                listing.getMake().getName(),
                listing.getModel().getId(),
                listing.getModel().getName(),
                listing.getSellerType(),
                listing.getYearOfManufacturing(),
                listing.getPlateNumber(),
                listing.getMileage(),
                listing.getCarType(),
                listing.getColor(),
                TechnicalSpecificationResponse.from(listing.getTechnicalSpecification()),
                RegistrationOwnershipResponse.from(listing.getRegistrationOwnership()),
                PriceCoeDetailsResponse.from(listing.getPriceCoeDetails()),
                OwnershipInfoResponse.from(listing.getOwnershipInfo()),
                SellerInfoResponse.from(listing.getSellerInfo()),
                images.stream().map(CarImageResponse::from).toList(),
                attributes.stream().collect(Collectors.toMap(
                        ListingAttribute::getAttributeKey,
                        a -> a.getAttributeValue() != null ? a.getAttributeValue() : ""
                )),
                listing.getCreatedAt(),
                listing.getUpdatedAt(),
                listing.getCreatedBy(),
                listing.getUpdatedBy()
        );
    }
}
