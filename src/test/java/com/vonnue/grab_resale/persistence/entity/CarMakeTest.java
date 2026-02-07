package com.vonnue.grab_resale.persistence.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.vonnue.grab_resale.config.JpaConfig;
import com.vonnue.grab_resale.persistence.repository.CarMakeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@ActiveProfiles("test")
class CarMakeTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CarMakeRepository carMakeRepository;

    @Test
    void shouldPersistCarMake() {
        CarMake make = new CarMake("Toyota");
        em.persistAndFlush(make);
        em.clear();

        CarMake found = em.find(CarMake.class, make.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Toyota");
        assertThat(found.getSlug()).isEqualTo("toyota");
        assertThat(found.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldGenerateSlugFromName() {
        CarMake make = new CarMake("Mercedes Benz");
        em.persistAndFlush(make);

        assertThat(make.getSlug()).isEqualTo("mercedes-benz");
    }

    @Test
    void shouldEnforceUniqueName() {
        CarMake make1 = new CarMake("Honda");
        em.persistAndFlush(make1);

        CarMake make2 = new CarMake("Honda");
        assertThatThrownBy(() -> em.persistAndFlush(make2))
                .isInstanceOf(Exception.class);
    }

    @Test
    void shouldFindBySlug() {
        CarMake make = new CarMake("BMW");
        em.persistAndFlush(make);
        em.clear();

        assertThat(carMakeRepository.findBySlug("bmw")).isPresent();
        assertThat(carMakeRepository.findBySlug("nonexistent")).isEmpty();
    }

    @Test
    void shouldCheckExistsByName() {
        CarMake make = new CarMake("Audi");
        em.persistAndFlush(make);

        assertThat(carMakeRepository.existsByName("Audi")).isTrue();
        assertThat(carMakeRepository.existsByName("Lexus")).isFalse();
    }
}
