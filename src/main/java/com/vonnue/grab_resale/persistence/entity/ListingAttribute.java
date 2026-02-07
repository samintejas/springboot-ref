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
@Table(name = "listing_attributes")
@Getter
@Setter
@NoArgsConstructor
public class ListingAttribute extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "listing_id", nullable = false)
    private CarListing listing;

    @Column(nullable = false)
    private String attributeKey;

    @Column(columnDefinition = "TEXT")
    private String attributeValue;
}
