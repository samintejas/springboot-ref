package com.vonnue.grab_resale.domain;

import java.util.stream.Stream;

import com.vonnue.grab_resale.common.constants.Transmission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransmissionTest {

    @Test
    void shouldHaveExactlyThreeValues() {
        assertThat(Transmission.values()).hasSize(3);
    }

    @ParameterizedTest
    @MethodSource("allTransmissions")
    void shouldContainValue(String name) {
        assertThat(Transmission.valueOf(name)).isNotNull();
    }

    static Stream<Arguments> allTransmissions() {
        return Stream.of(
                Arguments.of("AUTOMATIC"),
                Arguments.of("MANUAL"),
                Arguments.of("CVT")
        );
    }
}
