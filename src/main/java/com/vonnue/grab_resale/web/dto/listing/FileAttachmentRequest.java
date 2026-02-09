package com.vonnue.grab_resale.web.dto.listing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FileAttachmentRequest(
        @NotBlank @Size(max = 1024) String fileUrl,
        @NotBlank @Size(max = 255) String fileName,
        @NotBlank @Size(max = 100) String mimeType,
        @Positive Long size
) {
}
