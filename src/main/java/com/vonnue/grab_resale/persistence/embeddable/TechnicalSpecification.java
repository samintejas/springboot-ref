package com.vonnue.grab_resale.persistence.embeddable;

import com.vonnue.grab_resale.common.constants.FuelType;
import com.vonnue.grab_resale.common.constants.Transmission;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TechnicalSpecification {

    private String engineNumber;

    private String chassisNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Transmission transmission;

    private Integer engineCapacity;

    private Integer horsePower;
}
