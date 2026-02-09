package com.vonnue.grab_resale.web.dto.reference;

import java.util.List;

import com.vonnue.grab_resale.persistence.entity.CarMake;
import com.vonnue.grab_resale.persistence.entity.CarModel;

public record CarMakeResponse(
        Long id,
        String name,
        String slug,
        List<CarModelResponse> models
) {

    public static CarMakeResponse from(CarMake make, List<CarModel> models) {
        return new CarMakeResponse(
                make.getId(),
                make.getName(),
                make.getSlug(),
                models.stream().map(CarModelResponse::from).toList()
        );
    }
}