package com.vonnue.grab_resale.domain;

import java.util.stream.Stream;

import com.vonnue.grab_resale.common.constants.CarType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarTypeTest {

    @Test
    void shouldHaveExactlyEightValues() {
        assertThat(CarType.values()).hasSize(8);
    }

    @ParameterizedTest
    @MethodSource("allCarTypes")
    void shouldContainValue(String name) {
        assertThat(CarType.valueOf(name)).isNotNull();
    }

    static Stream<Arguments> allCarTypes() {
        return Stream.of(
                Arguments.of("SEDAN"),
                Arguments.of("SUV"),
                Arguments.of("HATCHBACK"),
                Arguments.of("COUPE"),
                Arguments.of("CONVERTIBLE"),
                Arguments.of("WAGON"),
                Arguments.of("VAN"),
                Arguments.of("TRUCK")
        );
    }
}
