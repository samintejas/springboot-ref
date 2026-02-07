package com.vonnue.grab_resale.web.dto.reference;

import com.vonnue.grab_resale.persistence.entity.CarMake;

public record CarMakeResponse(
        Long id,
        String name,
        String slug
) {

    public static CarMakeResponse from(CarMake make) {
        return new CarMakeResponse(make.getId(), make.getName(), make.getSlug());
    }
}
