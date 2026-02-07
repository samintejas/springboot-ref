package com.vonnue.grab_resale.web.dto.listing;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PriceCoeDetailsRequest(
        @NotNull @PositiveOrZero BigDecimal askingPrice,
        LocalDate coeRenewal,
        @PositiveOrZero BigDecimal coeAmount,
        @PositiveOrZero BigDecimal downPayment,
        @PositiveOrZero BigDecimal minimumParf,
        @PositiveOrZero BigDecimal depreciation,
        @PositiveOrZero BigDecimal roadTax,
        @PositiveOrZero BigDecimal omv
) {
}
