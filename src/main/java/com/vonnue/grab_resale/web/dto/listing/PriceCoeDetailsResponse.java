package com.vonnue.grab_resale.web.dto.listing;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.vonnue.grab_resale.persistence.embeddable.PriceCoeDetails;

public record PriceCoeDetailsResponse(
        BigDecimal askingPrice,
        LocalDate coeRenewal,
        BigDecimal coeAmount,
        BigDecimal downPayment,
        BigDecimal minimumParf,
        BigDecimal depreciation,
        BigDecimal roadTax,
        BigDecimal omv
) {

    public static PriceCoeDetailsResponse from(PriceCoeDetails details) {
        if (details == null) return null;
        return new PriceCoeDetailsResponse(
                details.getAskingPrice(),
                details.getCoeRenewal(),
                details.getCoeAmount(),
                details.getDownPayment(),
                details.getMinimumParf(),
                details.getDepreciation(),
                details.getRoadTax(),
                details.getOmv()
        );
    }
}
