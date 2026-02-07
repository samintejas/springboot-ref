package com.vonnue.grab_resale.persistence.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileAttachment {

    @Column(length = 1024)
    private String fileUrl;

    private String fileName;

    private String mimeType;

    private Long size;
}
