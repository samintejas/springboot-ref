package com.vonnue.grab_resale.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vonnue.grab_resale.persistence.entity.CarImage;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
    List<CarImage> findByListingId(Long listingId);

    void deleteByListingId(Long listingId);
}
