package com.vonnue.grab_resale.persistence.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class OwnershipInfo {

    private String registeredOwner;

    private String contactNumber;

    private String email;

    private String address;

    private Boolean isSeller;
}
