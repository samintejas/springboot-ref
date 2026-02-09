package com.vonnue.grab_resale.web.dto.listing;

import com.vonnue.grab_resale.persistence.entity.CarImage;

public record CarImageResponse(
        Long id,
        String imageUrl,
        String fileName,
        String mimeType,
        Long size,
        Boolean numberPlateImage
) {

    public static CarImageResponse from(CarImage image) {
        return new CarImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getFileName(),
                image.getMimeType(),
                image.getSize(),
                image.isNumberPlateImage()
        );
    }
}
