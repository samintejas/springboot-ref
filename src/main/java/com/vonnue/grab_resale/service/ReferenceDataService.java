package com.vonnue.grab_resale.service;

import java.util.List;

import com.vonnue.grab_resale.web.dto.reference.CarMakeResponse;

public interface ReferenceDataService {

    List<CarMakeResponse> getAllMakesWithModels();

    void evictCarMakesCache();
}