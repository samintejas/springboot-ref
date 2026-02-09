package com.vonnue.grab_resale.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vonnue.grab_resale.persistence.entity.CarModel;
import com.vonnue.grab_resale.persistence.repository.CarMakeRepository;
import com.vonnue.grab_resale.persistence.repository.CarModelRepository;
import com.vonnue.grab_resale.service.ReferenceDataService;
import com.vonnue.grab_resale.web.dto.reference.CarMakeResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReferenceDataServiceImpl implements ReferenceDataService {

    private final CarMakeRepository carMakeRepository;
    private final CarModelRepository carModelRepository;

    @Override
    @Cacheable("car-makes")
    public List<CarMakeResponse> getAllMakesWithModels() {
        var makes = carMakeRepository.findAll();
        var models = carModelRepository.findAll();

        Map<Long, List<CarModel>> modelsByMakeId = models.stream()
                .collect(Collectors.groupingBy(m -> m.getMake().getId()));

        return makes.stream()
                .map(make -> CarMakeResponse.from(make, modelsByMakeId.getOrDefault(make.getId(), List.of())))
                .toList();
    }

    @Override
    @CacheEvict(value = "car-makes", allEntries = true)
    public void evictCarMakesCache() {
        // cache eviction only
    }
}