package com.vonnue.grab_resale.persistence.embeddable;

import java.time.LocalDate;

import jakarta.persistence.Embedded;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class RegistrationOwnership {

    private String ownerName;

    private LocalDate registrationDate;

    private Integer numberOfTransfers;

    private String contactNumber;

    private String emailAddress;

    private String address;

    @Embedded
    private FileAttachment logCard;
}
