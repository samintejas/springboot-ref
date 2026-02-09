package com.vonnue.grab_resale.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import com.vonnue.grab_resale.common.constants.CarCondition;
import com.vonnue.grab_resale.common.constants.CarType;
import com.vonnue.grab_resale.common.constants.Role;
import com.vonnue.grab_resale.common.constants.SellerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import com.vonnue.grab_resale.config.JpaConfig;
import com.vonnue.grab_resale.persistence.entity.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@ActiveProfiles("test")
class CarListingRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CarListingRepository carListingRepository;

    @Autowired
    private CarImageRepository carImageRepository;

    @Autowired
    private ListingAttributeRepository listingAttributeRepository;

    private User user;
    private CarMake toyota;
    private CarMake honda;
    private CarModel camry;
    private CarModel civic;

    @BeforeEach
    void setUp() {
        user = new User("test@example.com", "password123", "Test User", Role.USER);
        em.persistAndFlush(user);

        toyota = new CarMake("Toyota");
        em.persistAndFlush(toyota);
        honda = new CarMake("Honda");
        em.persistAndFlush(honda);

        camry = new CarModel("Camry", toyota);
        em.persistAndFlush(camry);
        civic = new CarModel("Civic", honda);
        em.persistAndFlush(civic);
    }

    private CarListing createListing(CarCondition condition, CarMake make, CarModel model) {
        CarListing listing = new CarListing();
        listing.setCarCondition(condition);
        listing.setUser(user);
        listing.setMake(make);
        listing.setModel(model);
        listing.setSellerType(SellerType.INDIVIDUAL);
        listing.setYearOfManufacturing(LocalDate.of(2020, 1, 1));
        listing.setPlateNumber("SGX1234A");
        listing.setMileage(50000);
        listing.setCarType(CarType.SEDAN);
        listing.setColor("White");
        return listing;
    }

    @Nested
    class CarListingQueryTests {

        @Test
        void shouldFindAllWithPagination() {
            em.persistAndFlush(createListing(CarCondition.NEW_CAR, toyota, camry));
            em.persistAndFlush(createListing(CarCondition.PRE_OWNED_CAR, honda, civic));
            em.clear();

            Page<CarListing> page = carListingRepository.findAllBy(
                    PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
            assertThat(page.getContent()).hasSize(2);
            assertThat(page.getContent().getFirst().getMake()).isNotNull();
            assertThat(page.getContent().getFirst().getModel()).isNotNull();
        }

        @Test
        void shouldFindWithMakeAndModelById() {
            CarListing listing = createListing(CarCondition.NEW_CAR, toyota, camry);
            em.persistAndFlush(listing);
            em.clear();

            CarListing found = carListingRepository.findWithMakeAndModelById(listing.getId()).orElseThrow();
            assertThat(found.getMake().getName()).isEqualTo("Toyota");
            assertThat(found.getModel().getName()).isEqualTo("Camry");
        }
    }

    @Nested
    class CarImageTests {

        @Test
        void shouldPersistCarImage() {
            CarListing listing = createListing(CarCondition.PRE_OWNED_CAR, toyota, camry);
            em.persistAndFlush(listing);

            CarImage image = new CarImage();
            image.setListing(listing);
            image.setImageUrl("https://example.com/img1.jpg");
            image.setFileName("img1.jpg");
            image.setMimeType("image/jpeg");
            image.setSize(102400L);
            image.setNumberPlateImage(false);
            em.persistAndFlush(image);
            em.clear();

            CarImage found = em.find(CarImage.class, image.getId());
            assertThat(found).isNotNull();
            assertThat(found.getImageUrl()).isEqualTo("https://example.com/img1.jpg");
            assertThat(found.getListing().getId()).isEqualTo(listing.getId());
            assertThat(found.isNumberPlateImage()).isFalse();
        }

        @Test
        void shouldFindByListingId() {
            CarListing listing = createListing(CarCondition.PRE_OWNED_CAR, toyota, camry);
            em.persistAndFlush(listing);

            CarImage img1 = new CarImage();
            img1.setListing(listing);
            img1.setImageUrl("https://example.com/img1.jpg");
            img1.setFileName("img1.jpg");
            img1.setMimeType("image/jpeg");
            img1.setSize(102400L);
            img1.setNumberPlateImage(true);
            em.persistAndFlush(img1);

            CarImage img2 = new CarImage();
            img2.setListing(listing);
            img2.setImageUrl("https://example.com/img2.jpg");
            img2.setFileName("img2.jpg");
            img2.setMimeType("image/jpeg");
            img2.setSize(204800L);
            img2.setNumberPlateImage(false);
            em.persistAndFlush(img2);
            em.clear();

            List<CarImage> images = carImageRepository.findByListingId(listing.getId());
            assertThat(images).hasSize(2);
        }

        @Test
        void shouldFindByListingIdIn() {
            CarListing listing1 = createListing(CarCondition.PRE_OWNED_CAR, toyota, camry);
            em.persistAndFlush(listing1);
            CarListing listing2 = createListing(CarCondition.NEW_CAR, honda, civic);
            listing2.setPlateNumber("SGY5678B");
            em.persistAndFlush(listing2);

            CarImage img1 = new CarImage();
            img1.setListing(listing1);
            img1.setImageUrl("https://example.com/img1.jpg");
            img1.setFileName("img1.jpg");
            img1.setMimeType("image/jpeg");
            img1.setNumberPlateImage(false);
            em.persistAndFlush(img1);

            CarImage img2 = new CarImage();
            img2.setListing(listing2);
            img2.setImageUrl("https://example.com/img2.jpg");
            img2.setFileName("img2.jpg");
            img2.setMimeType("image/jpeg");
            img2.setNumberPlateImage(false);
            em.persistAndFlush(img2);
            em.clear();

            List<CarImage> images = carImageRepository.findByListingIdIn(
                    List.of(listing1.getId(), listing2.getId()));
            assertThat(images).hasSize(2);
        }
    }

    @Nested
    class ListingAttributeTests {

        @Test
        void shouldPersistListingAttribute() {
            CarListing listing = createListing(CarCondition.PRE_OWNED_CAR, toyota, camry);
            em.persistAndFlush(listing);

            ListingAttribute attr = new ListingAttribute();
            attr.setListing(listing);
            attr.setAttributeKey("description");
            attr.setAttributeValue("Well maintained car");
            em.persistAndFlush(attr);
            em.clear();

            ListingAttribute found = em.find(ListingAttribute.class, attr.getId());
            assertThat(found).isNotNull();
            assertThat(found.getAttributeKey()).isEqualTo("description");
            assertThat(found.getAttributeValue()).isEqualTo("Well maintained car");
        }

        @Test
        void shouldFindByListingId() {
            CarListing listing = createListing(CarCondition.PRE_OWNED_CAR, toyota, camry);
            em.persistAndFlush(listing);

            ListingAttribute attr1 = new ListingAttribute();
            attr1.setListing(listing);
            attr1.setAttributeKey("description");
            attr1.setAttributeValue("Well maintained car");
            em.persistAndFlush(attr1);

            ListingAttribute attr2 = new ListingAttribute();
            attr2.setListing(listing);
            attr2.setAttributeKey("accessories");
            attr2.setAttributeValue("Leather seats, sunroof");
            em.persistAndFlush(attr2);
            em.clear();

            List<ListingAttribute> attrs = listingAttributeRepository.findByListingId(listing.getId());
            assertThat(attrs).hasSize(2);
        }

        @Test
        void shouldDeleteByListingId() {
            CarListing listing = createListing(CarCondition.PRE_OWNED_CAR, toyota, camry);
            em.persistAndFlush(listing);

            ListingAttribute attr = new ListingAttribute();
            attr.setListing(listing);
            attr.setAttributeKey("features");
            attr.setAttributeValue("GPS, reverse camera");
            em.persistAndFlush(attr);

            listingAttributeRepository.deleteByListingId(listing.getId());
            em.flush();
            em.clear();

            List<ListingAttribute> attrs = listingAttributeRepository.findByListingId(listing.getId());
            assertThat(attrs).isEmpty();
        }
    }
}
