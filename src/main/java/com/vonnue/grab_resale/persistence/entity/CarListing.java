package com.vonnue.grab_resale.persistence.entity;

import java.time.LocalDate;

import com.vonnue.grab_resale.common.constants.CarCondition;
import com.vonnue.grab_resale.common.constants.CarType;
import com.vonnue.grab_resale.common.constants.SellerType;
import com.vonnue.grab_resale.persistence.embeddable.OwnershipInfo;
import com.vonnue.grab_resale.persistence.embeddable.PriceCoeDetails;
import com.vonnue.grab_resale.persistence.embeddable.RegistrationOwnership;
import com.vonnue.grab_resale.persistence.embeddable.SellerInfo;
import com.vonnue.grab_resale.persistence.embeddable.TechnicalSpecification;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car_listings")
@Getter
@Setter
@NoArgsConstructor
public class CarListing extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CarCondition carCondition;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "make_id", nullable = false)
    private CarMake make;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SellerType sellerType;

    private LocalDate yearOfManufacturing;

    private String plateNumber;

    private Integer mileage;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CarType carType;

    private String color;

    @Embedded
    private TechnicalSpecification technicalSpecification;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "contactNumber", column = @Column(name = "reg_contact_number")),
            @AttributeOverride(name = "emailAddress", column = @Column(name = "reg_email_address")),
            @AttributeOverride(name = "address", column = @Column(name = "reg_address")),
            @AttributeOverride(name = "logCard.fileUrl", column = @Column(name = "log_card_file_url", length = 1024)),
            @AttributeOverride(name = "logCard.fileName", column = @Column(name = "log_card_file_name")),
            @AttributeOverride(name = "logCard.mimeType", column = @Column(name = "log_card_mime_type")),
            @AttributeOverride(name = "logCard.size", column = @Column(name = "log_card_size"))
    })
    private RegistrationOwnership registrationOwnership;

    @Embedded
    private PriceCoeDetails priceCoeDetails;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "contactNumber", column = @Column(name = "owner_contact_number")),
            @AttributeOverride(name = "email", column = @Column(name = "owner_email")),
            @AttributeOverride(name = "address", column = @Column(name = "owner_address"))
    })
    private OwnershipInfo ownershipInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "seller_name")),
            @AttributeOverride(name = "contactNumber", column = @Column(name = "seller_contact_number")),
            @AttributeOverride(name = "email", column = @Column(name = "seller_email")),
            @AttributeOverride(name = "letterOfAuthorization.fileUrl", column = @Column(name = "loa_file_url", length = 1024)),
            @AttributeOverride(name = "letterOfAuthorization.fileName", column = @Column(name = "loa_file_name")),
            @AttributeOverride(name = "letterOfAuthorization.mimeType", column = @Column(name = "loa_mime_type")),
            @AttributeOverride(name = "letterOfAuthorization.size", column = @Column(name = "loa_size"))
    })
    private SellerInfo sellerInfo;
}
