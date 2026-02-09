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
import jakarta.validation.constraints.Size;

public record UpdateCarListingRequest(

        CarCondition carCondition,
        Long makeId,
        Long modelId,
        LocalDate yearOfManufacturing,
        String plateNumber,
        @Positive Integer mileage,
        CarType carType,
        String color,
        String engineNumber,
        String chassisNumber,
        FuelType fuelType,
        Transmission transmission,
        @Positive Integer engineCapacity,
        @Positive Integer horsePower,
        String regOwnerName,
        LocalDate regRegistrationDate,
        @PositiveOrZero Integer regNumberOfTransfers,
        String regContactNumber,
        String regEmailAddress,
        String regAddress,
        @Valid FileAttachmentRequest regLogCard,
        @PositiveOrZero BigDecimal askingPrice,
        LocalDate coeRenewal,
        @PositiveOrZero BigDecimal coeAmount,
        @PositiveOrZero BigDecimal downPayment,
        @PositiveOrZero BigDecimal minimumParf,
        @PositiveOrZero BigDecimal depreciation,
        @PositiveOrZero BigDecimal roadTax,
        @PositiveOrZero BigDecimal omv,
        SellerType sellerType,
        String ownerRegisteredOwner,
        String ownerContactNumber,
        String ownerEmail,
        String ownerAddress,
        Boolean ownerIsSeller,
        String sellerName,
        String sellerContactNumber,
        String sellerWhatsAppNumber,
        String sellerCompanyAddress,
        String sellerEmail,
        @Valid FileAttachmentRequest sellerLetterOfAuthorization,
        @Size(max = 50) Map<String, String> otherDetails
) {
}
