package com.vonnue.grab_resale.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vonnue.grab_resale.persistence.entity.ListingAttribute;

public interface ListingAttributeRepository extends JpaRepository<ListingAttribute, Long> {

    List<ListingAttribute> findByListingId(Long listingId);

    @Modifying
    @Query("DELETE FROM ListingAttribute la WHERE la.listing.id = :listingId")
    void deleteByListingId(Long listingId);
}
