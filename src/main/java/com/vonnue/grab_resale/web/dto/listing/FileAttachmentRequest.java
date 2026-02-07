package com.vonnue.grab_resale.web.dto.listing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record FileAttachmentRequest(
        @NotBlank String fileUrl,
        @NotBlank String fileName,
        @NotBlank String mimeType,
        @Positive Long size
) {
}
