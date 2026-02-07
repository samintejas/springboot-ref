package com.vonnue.grab_resale.persistence.embeddable;

import jakarta.persistence.Embedded;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class SellerInfo {

    private String name;

    private String contactNumber;

    private String whatsAppNumber;

    private String companyAddress;

    private String email;

    @Embedded
    private FileAttachment letterOfAuthorization;
}
