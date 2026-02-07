package com.vonnue.grab_resale.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vonnue.grab_resale.persistence.entity.ListingAttribute;

public interface ListingAttributeRepository extends JpaRepository<ListingAttribute, Long> {

    List<ListingAttribute> findByListingId(Long listingId);

    void deleteByListingId(Long listingId);
}
