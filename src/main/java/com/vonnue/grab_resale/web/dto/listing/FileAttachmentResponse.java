package com.vonnue.grab_resale.web.dto.listing;

import com.vonnue.grab_resale.persistence.embeddable.FileAttachment;

public record FileAttachmentResponse(
        String fileUrl,
        String fileName,
        String mimeType,
        Long size
) {

    public static FileAttachmentResponse from(FileAttachment attachment) {
        if (attachment == null) return null;
        return new FileAttachmentResponse(
                attachment.getFileUrl(),
                attachment.getFileName(),
                attachment.getMimeType(),
                attachment.getSize()
        );
    }
}
