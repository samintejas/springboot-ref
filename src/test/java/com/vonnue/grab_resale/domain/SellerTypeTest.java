package com.vonnue.grab_resale.domain;

import com.vonnue.grab_resale.common.constants.SellerType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SellerTypeTest {

    @Test
    void shouldHaveExactlyTwoValues() {
        assertThat(SellerType.values()).hasSize(2);
    }

    @Test
    void shouldContainExpectedValues() {
        assertThat(SellerType.values())
                .containsExactly(SellerType.INDIVIDUAL, SellerType.COMPANY);
    }

    @Test
    void shouldResolveFromString() {
        assertThat(SellerType.valueOf("INDIVIDUAL")).isEqualTo(SellerType.INDIVIDUAL);
        assertThat(SellerType.valueOf("COMPANY")).isEqualTo(SellerType.COMPANY);
    }
}
