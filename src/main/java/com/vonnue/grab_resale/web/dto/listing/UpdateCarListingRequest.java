package com.vonnue.grab_resale.web.dto.listing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.vonnue.grab_resale.common.constants.CarCondition;
import com.vonnue.grab_resale.common.constants.CarType;
import com.vonnue.grab_resale.common.constants.FuelType;
import com.vonnue.grab_resale.common.constants.SellerType;
import com.vonnue.grab_resale.common.constants.Transmission;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateCarListingRequest(
        // Car details
        CarCondition carCondition,
        Long makeId,
        Long modelId,
        LocalDate yearOfManufacturing,
        String plateNumber,
        @Positive Integer mileage,
        CarType carType,
        String color,

        // Technical specification
        String engineNumber,
        String chassisNumber,
        FuelType fuelType,
        Transmission transmission,
        @Positive Integer engineCapacity,
        @Positive Integer horsePower,

        // Registration & ownership
        String regOwnerName,
        LocalDate regRegistrationDate,
        Integer regNumberOfTransfers,
        String regContactNumber,
        String regEmailAddress,
        String regAddress,
        @Valid FileAttachmentRequest regLogCard,

        // Price & COE details
        @PositiveOrZero BigDecimal askingPrice,
        LocalDate coeRenewal,
        @PositiveOrZero BigDecimal coeAmount,
        @PositiveOrZero BigDecimal downPayment,
        @PositiveOrZero BigDecimal minimumParf,
        @PositiveOrZero BigDecimal depreciation,
        @PositiveOrZero BigDecimal roadTax,
        @PositiveOrZero BigDecimal omv,

        // Seller details
        SellerType sellerType,

        // Ownership info
        String ownerRegisteredOwner,
        String ownerContactNumber,
        String ownerEmail,
        String ownerAddress,
        Boolean ownerIsSeller,

        // Seller info
        String sellerName,
        String sellerContactNumber,
        String sellerWhatsAppNumber,
        String sellerCompanyAddress,
        String sellerEmail,
        @Valid FileAttachmentRequest sellerLetterOfAuthorization,

        // Other details
        Map<String, String> otherDetails
) {
}
