package com.vonnue.grab_resale.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vonnue.grab_resale.persistence.entity.CarModel;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    List<CarModel> findByMakeId(Long makeId);

    Optional<CarModel> findBySlugAndMakeId(String slug, Long makeId);
}
