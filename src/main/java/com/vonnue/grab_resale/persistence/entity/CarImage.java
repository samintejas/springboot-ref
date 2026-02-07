package com.vonnue.grab_resale.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car_images")
@Getter
@Setter
@NoArgsConstructor
public class CarImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "listing_id", nullable = false)
    private CarListing listing;

    @Column(nullable = false, length = 1024)
    private String imageUrl;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String mimeType;

    private Long size;

    @Column(nullable = false)
    private Boolean numberPlateImage;
}
