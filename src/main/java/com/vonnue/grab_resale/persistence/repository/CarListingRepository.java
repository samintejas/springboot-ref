package com.vonnue.grab_resale.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vonnue.grab_resale.persistence.entity.CarListing;

public interface CarListingRepository extends JpaRepository<CarListing, Long> {

    @EntityGraph(attributePaths = {"make", "model"})
    @Query("SELECT cl FROM CarListing cl")
    Page<CarListing> findAllBy(Pageable pageable);

    @EntityGraph(attributePaths = {"make", "model"})
    Optional<CarListing> findWithMakeAndModelById(Long id);
}
