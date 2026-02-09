package com.vonnue.grab_resale.web.dto.reference;

import com.vonnue.grab_resale.persistence.entity.CarModel;

public record CarModelResponse(
        Long id,
        String name,
        String slug
) {

    public static CarModelResponse from(CarModel model) {
        return new CarModelResponse(model.getId(), model.getName(), model.getSlug());
    }
}