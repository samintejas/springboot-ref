package com.vonnue.grab_resale.domain;

import com.vonnue.grab_resale.common.constants.CarCondition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarConditionTest {

    @Test
    void shouldHaveExactlyTwoValues() {
        assertThat(CarCondition.values()).hasSize(2);
    }

    @Test
    void shouldContainExpectedValues() {
        assertThat(CarCondition.values())
                .containsExactly(CarCondition.NEW_CAR, CarCondition.PRE_OWNED_CAR);
    }

    @Test
    void shouldResolveFromString() {
        assertThat(CarCondition.valueOf("NEW_CAR")).isEqualTo(CarCondition.NEW_CAR);
        assertThat(CarCondition.valueOf("PRE_OWNED_CAR")).isEqualTo(CarCondition.PRE_OWNED_CAR);
    }
}
