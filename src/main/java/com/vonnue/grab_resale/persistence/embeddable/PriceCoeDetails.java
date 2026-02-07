package com.vonnue.grab_resale.persistence.embeddable;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class PriceCoeDetails {

    @Column(precision = 15, scale = 2)
    private BigDecimal askingPrice;

    private LocalDate coeRenewal;

    @Column(precision = 15, scale = 2)
    private BigDecimal coeAmount;

    @Column(precision = 15, scale = 2)
    private BigDecimal downPayment;

    @Column(precision = 15, scale = 2)
    private BigDecimal minimumParf;

    @Column(precision = 15, scale = 2)
    private BigDecimal depreciation;

    @Column(precision = 15, scale = 2)
    private BigDecimal roadTax;

    @Column(precision = 15, scale = 2)
    private BigDecimal omv;
}
