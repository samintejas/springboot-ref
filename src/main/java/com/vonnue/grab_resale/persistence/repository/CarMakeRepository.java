package com.vonnue.grab_resale.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vonnue.grab_resale.persistence.entity.CarMake;

public interface CarMakeRepository extends JpaRepository<CarMake, Long> {

    Optional<CarMake> findBySlug(String slug);

    boolean existsByName(String name);
}
