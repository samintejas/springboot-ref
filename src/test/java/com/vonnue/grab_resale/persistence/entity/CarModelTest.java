package com.vonnue.grab_resale.persistence.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.vonnue.grab_resale.config.JpaConfig;
import com.vonnue.grab_resale.persistence.repository.CarModelRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@ActiveProfiles("test")
class CarModelTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CarModelRepository carModelRepository;

    @Test
    void shouldPersistCarModelWithMake() {
        CarMake make = new CarMake("Toyota");
        em.persistAndFlush(make);

        CarModel model = new CarModel("Camry", make);
        em.persistAndFlush(model);
        em.clear();

        CarModel found = em.find(CarModel.class, model.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Camry");
        assertThat(found.getSlug()).isEqualTo("camry");
        assertThat(found.getMake().getName()).isEqualTo("Toyota");
    }

    @Test
    void shouldRequireMake() {
        assertThatThrownBy(() -> {
            CarModel model = new CarModel("Civic", null);
            em.persistAndFlush(model);
        }).isInstanceOf(Exception.class);
    }

    @Test
    void shouldGenerateSlugFromName() {
        CarMake make = new CarMake("Land Rover");
        em.persistAndFlush(make);

        CarModel model = new CarModel("Range Rover Sport", make);
        em.persistAndFlush(model);

        assertThat(model.getSlug()).isEqualTo("range-rover-sport");
    }

    @Test
    void shouldFindByMakeId() {
        CarMake toyota = new CarMake("Toyota");
        em.persistAndFlush(toyota);
        CarMake honda = new CarMake("Honda");
        em.persistAndFlush(honda);

        em.persistAndFlush(new CarModel("Camry", toyota));
        em.persistAndFlush(new CarModel("Corolla", toyota));
        em.persistAndFlush(new CarModel("Civic", honda));
        em.clear();

        List<CarModel> toyotaModels = carModelRepository.findByMakeId(toyota.getId());
        assertThat(toyotaModels).hasSize(2);
    }

    @Test
    void shouldFindBySlugAndMakeId() {
        CarMake make = new CarMake("BMW");
        em.persistAndFlush(make);

        em.persistAndFlush(new CarModel("3 Series", make));
        em.clear();

        assertThat(carModelRepository.findBySlugAndMakeId("3-series", make.getId())).isPresent();
        assertThat(carModelRepository.findBySlugAndMakeId("5-series", make.getId())).isEmpty();
    }
}
