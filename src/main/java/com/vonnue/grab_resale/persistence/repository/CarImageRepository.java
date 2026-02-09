package com.vonnue.grab_resale.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vonnue.grab_resale.persistence.entity.CarImage;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {

    List<CarImage> findByListingId(Long listingId);

    List<CarImage> findByListingIdIn(Collection<Long> listingIds);

    @Modifying
    @Query("DELETE FROM CarImage ci WHERE ci.listing.id = :listingId")
    void deleteByListingId(Long listingId);
}
