package com.vonnue.grab_resale.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vonnue.grab_resale.common.constants.CarCondition;
import com.vonnue.grab_resale.persistence.entity.CarListing;

public interface CarListingRepository extends JpaRepository<CarListing, Long> {

    List<CarListing> findByUserId(Long userId);

    List<CarListing> findByCarCondition(CarCondition carCondition);

    Page<CarListing> findByCarCondition(CarCondition carCondition, Pageable pageable);

    List<CarListing> findByMakeId(Long makeId);

    List<CarListing> findByModelId(Long modelId);
}
