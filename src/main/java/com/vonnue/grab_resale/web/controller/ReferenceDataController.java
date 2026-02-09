package com.vonnue.grab_resale.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vonnue.grab_resale.service.ReferenceDataService;
import com.vonnue.grab_resale.web.dto.reference.CarMakeResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reference")
@RequiredArgsConstructor
@Tag(name = "Reference Data", description = "Public reference data for car makes and models")
public class ReferenceDataController {

    private final ReferenceDataService referenceDataService;

    @GetMapping("/car-makes")
    @Operation(summary = "Get all car makes with models", description = "Returns all makes with nested models. Response is cached.")
    public List<CarMakeResponse> getCarMakes() {
        return referenceDataService.getAllMakesWithModels();
    }
}