package com.vonnue.grab_resale.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.vonnue.grab_resale.common.constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.vonnue.grab_resale.config.JpaConfig;
import com.vonnue.grab_resale.persistence.embeddable.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@ActiveProfiles("test")
class CarListingTest {

    @Autowired
    private TestEntityManager em;

    private User user;
    private CarMake make;
    private CarModel model;

    @BeforeEach
    void setUp() {
        user = new User("test@example.com", "password123", "Test User", Role.USER);
        em.persistAndFlush(user);

        make = new CarMake("Toyota");
        em.persistAndFlush(make);

        model = new CarModel("Camry", make);
        em.persistAndFlush(model);
    }

    private CarListing minimalListing() {
        CarListing listing = new CarListing();
        listing.setCarCondition(CarCondition.PRE_OWNED_CAR);
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

    @Test
    void shouldPersistMinimalListing() {
        CarListing listing = minimalListing();
        em.persistAndFlush(listing);
        em.clear();

        CarListing found = em.find(CarListing.class, listing.getId());
        assertThat(found).isNotNull();
        assertThat(found.getCarCondition()).isEqualTo(CarCondition.PRE_OWNED_CAR);
        assertThat(found.getUser().getId()).isEqualTo(user.getId());
        assertThat(found.getMake().getName()).isEqualTo("Toyota");
        assertThat(found.getModel().getName()).isEqualTo("Camry");
        assertThat(found.getPlateNumber()).isEqualTo("SGX1234A");
        assertThat(found.getMileage()).isEqualTo(50000);
        assertThat(found.getCarType()).isEqualTo(CarType.SEDAN);
        assertThat(found.getColor()).isEqualTo("White");
        assertThat(found.getCreatedAt()).isNotNull();
    }

    @Nested
    class WithTechnicalSpecification {
        @Test
        void shouldPersistWithTechSpec() {
            CarListing listing = minimalListing();

            TechnicalSpecification spec = new TechnicalSpecification();
            spec.setEngineNumber("ENG123");
            spec.setChassisNumber("CHS456");
            spec.setFuelType(FuelType.PETROL);
            spec.setTransmission(Transmission.AUTOMATIC);
            spec.setEngineCapacity(2000);
            spec.setHorsePower(150);
            listing.setTechnicalSpecification(spec);

            em.persistAndFlush(listing);
            em.clear();

            CarListing found = em.find(CarListing.class, listing.getId());
            assertThat(found.getTechnicalSpecification()).isNotNull();
            assertThat(found.getTechnicalSpecification().getEngineNumber()).isEqualTo("ENG123");
            assertThat(found.getTechnicalSpecification().getFuelType()).isEqualTo(FuelType.PETROL);
            assertThat(found.getTechnicalSpecification().getTransmission()).isEqualTo(Transmission.AUTOMATIC);
        }
    }

    @Nested
    class WithRegistrationOwnership {
        @Test
        void shouldPersistWithRegistration() {
            CarListing listing = minimalListing();

            RegistrationOwnership reg = new RegistrationOwnership();
            reg.setOwnerName("John Doe");
            reg.setRegistrationDate(LocalDate.of(2020, 3, 15));
            reg.setNumberOfTransfers(2);
            reg.setContactNumber("91234567");
            reg.setEmailAddress("john@example.com");
            reg.setAddress("123 Main St");
            reg.setLogCard(new FileAttachment("https://example.com/log.pdf", "log.pdf", "application/pdf", 2048L));
            listing.setRegistrationOwnership(reg);

            em.persistAndFlush(listing);
            em.clear();

            CarListing found = em.find(CarListing.class, listing.getId());
            assertThat(found.getRegistrationOwnership()).isNotNull();
            assertThat(found.getRegistrationOwnership().getOwnerName()).isEqualTo("John Doe");
            assertThat(found.getRegistrationOwnership().getContactNumber()).isEqualTo("91234567");
            assertThat(found.getRegistrationOwnership().getLogCard().getFileName()).isEqualTo("log.pdf");
        }
    }

    @Nested
    class WithPriceCoeDetails {
        @Test
        void shouldPersistWithPriceDetails() {
            CarListing listing = minimalListing();

            PriceCoeDetails price = new PriceCoeDetails();
            price.setAskingPrice(new BigDecimal("50000.00"));
            price.setCoeRenewal(LocalDate.of(2030, 6, 1));
            price.setCoeAmount(new BigDecimal("40000.00"));
            price.setDownPayment(new BigDecimal("10000.00"));
            price.setMinimumParf(new BigDecimal("5000.00"));
            price.setDepreciation(new BigDecimal("8000.00"));
            price.setRoadTax(new BigDecimal("1500.00"));
            price.setOmv(new BigDecimal("25000.00"));
            listing.setPriceCoeDetails(price);

            em.persistAndFlush(listing);
            em.clear();

            CarListing found = em.find(CarListing.class, listing.getId());
            assertThat(found.getPriceCoeDetails()).isNotNull();
            assertThat(found.getPriceCoeDetails().getAskingPrice()).isEqualByComparingTo("50000.00");
            assertThat(found.getPriceCoeDetails().getOmv()).isEqualByComparingTo("25000.00");
        }
    }

    @Nested
    class WithOwnershipAndSellerInfo {
        @Test
        void shouldPersistWithOwnershipInfo() {
            CarListing listing = minimalListing();

            OwnershipInfo ownership = new OwnershipInfo();
            ownership.setRegisteredOwner("Jane Doe");
            ownership.setContactNumber("98765432");
            ownership.setEmail("jane@example.com");
            ownership.setAddress("456 Oak Ave");
            ownership.setSeller(true);
            listing.setOwnershipInfo(ownership);

            em.persistAndFlush(listing);
            em.clear();

            CarListing found = em.find(CarListing.class, listing.getId());
            assertThat(found.getOwnershipInfo()).isNotNull();
            assertThat(found.getOwnershipInfo().getRegisteredOwner()).isEqualTo("Jane Doe");
            assertThat(found.getOwnershipInfo().getSeller()).isTrue();
        }

        @Test
        void shouldPersistWithSellerInfo() {
            CarListing listing = minimalListing();

            SellerInfo seller = new SellerInfo();
            seller.setName("Acme Cars");
            seller.setContactNumber("61234567");
            seller.setWhatsAppNumber("61234567");
            seller.setCompanyAddress("789 Business Rd");
            seller.setEmail("info@acme.com");
            seller.setLetterOfAuthorization(new FileAttachment("https://example.com/loa.pdf", "loa.pdf", "application/pdf", 512L));
            listing.setSellerInfo(seller);

            em.persistAndFlush(listing);
            em.clear();

            CarListing found = em.find(CarListing.class, listing.getId());
            assertThat(found.getSellerInfo()).isNotNull();
            assertThat(found.getSellerInfo().getName()).isEqualTo("Acme Cars");
            assertThat(found.getSellerInfo().getLetterOfAuthorization().getFileName()).isEqualTo("loa.pdf");
        }
    }
}
