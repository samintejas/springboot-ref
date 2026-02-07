package com.vonnue.grab_resale.persistence.embeddable;

import jakarta.persistence.Embeddable;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileAttachmentTest {

    @Test
    void shouldBeAnnotatedAsEmbeddable() {
        assertThat(FileAttachment.class.isAnnotationPresent(Embeddable.class)).isTrue();
    }

    @Test
    void shouldSupportNoArgConstruction() {
        FileAttachment attachment = new FileAttachment();
        assertThat(attachment).isNotNull();
    }

    @Test
    void shouldStoreAllFields() {
        FileAttachment attachment = new FileAttachment("https://example.com/file.pdf", "file.pdf", "application/pdf", 1024L);

        assertThat(attachment.getFileUrl()).isEqualTo("https://example.com/file.pdf");
        assertThat(attachment.getFileName()).isEqualTo("file.pdf");
        assertThat(attachment.getMimeType()).isEqualTo("application/pdf");
        assertThat(attachment.getSize()).isEqualTo(1024L);
    }
}
