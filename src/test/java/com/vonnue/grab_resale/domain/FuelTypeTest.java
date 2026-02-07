package com.vonnue.grab_resale.domain;

import java.util.stream.Stream;

import com.vonnue.grab_resale.common.constants.FuelType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FuelTypeTest {

    @Test
    void shouldHaveExactlyFiveValues() {
        assertThat(FuelType.values()).hasSize(5);
    }

    @ParameterizedTest
    @MethodSource("allFuelTypes")
    void shouldContainValue(String name) {
        assertThat(FuelType.valueOf(name)).isNotNull();
    }

    static Stream<Arguments> allFuelTypes() {
        return Stream.of(
                Arguments.of("PETROL"),
                Arguments.of("DIESEL"),
                Arguments.of("ELECTRIC"),
                Arguments.of("HYBRID"),
                Arguments.of("PLUGIN_HYBRID")
        );
    }
}
