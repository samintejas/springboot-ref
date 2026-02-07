package com.vonnue.grab_resale.persistence.embeddable;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.vonnue.grab_resale.common.constants.FuelType;
import com.vonnue.grab_resale.common.constants.Transmission;

import jakarta.persistence.Embeddable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmbeddableComponentsTest {

    @Nested
    class TechnicalSpecificationTests {

        @Test
        void shouldBeAnnotatedAsEmbeddable() {
            assertThat(TechnicalSpecification.class.isAnnotationPresent(Embeddable.class)).isTrue();
        }

        @Test
        void shouldSupportNoArgConstruction() {
            assertThat(new TechnicalSpecification()).isNotNull();
        }

        @Test
        void shouldStoreAllFields() {
            TechnicalSpecification spec = new TechnicalSpecification();
            spec.setEngineNumber("ENG123");
            spec.setChassisNumber("CHS456");
            spec.setFuelType(FuelType.PETROL);
            spec.setTransmission(Transmission.AUTOMATIC);
            spec.setEngineCapacity(2000);
            spec.setHorsePower(150);

            assertThat(spec.getEngineNumber()).isEqualTo("ENG123");
            assertThat(spec.getChassisNumber()).isEqualTo("CHS456");
            assertThat(spec.getFuelType()).isEqualTo(FuelType.PETROL);
            assertThat(spec.getTransmission()).isEqualTo(Transmission.AUTOMATIC);
            assertThat(spec.getEngineCapacity()).isEqualTo(2000);
            assertThat(spec.getHorsePower()).isEqualTo(150);
        }
    }

    @Nested
    class RegistrationOwnershipTests {

        @Test
        void shouldBeAnnotatedAsEmbeddable() {
            assertThat(RegistrationOwnership.class.isAnnotationPresent(Embeddable.class)).isTrue();
        }

        @Test
        void shouldSupportNoArgConstruction() {
            assertThat(new RegistrationOwnership()).isNotNull();
        }

        @Test
        void shouldStoreAllFields() {
            FileAttachment logCard = new FileAttachment("https://example.com/log.pdf", "log.pdf", "application/pdf", 2048L);

            RegistrationOwnership reg = new RegistrationOwnership();
            reg.setOwnerName("John Doe");
            reg.setRegistrationDate(LocalDate.of(2023, 1, 15));
            reg.setNumberOfTransfers(2);
            reg.setContactNumber("91234567");
            reg.setEmailAddress("john@example.com");
            reg.setAddress("123 Main St");
            reg.setLogCard(logCard);

            assertThat(reg.getOwnerName()).isEqualTo("John Doe");
            assertThat(reg.getRegistrationDate()).isEqualTo(LocalDate.of(2023, 1, 15));
            assertThat(reg.getNumberOfTransfers()).isEqualTo(2);
            assertThat(reg.getContactNumber()).isEqualTo("91234567");
            assertThat(reg.getEmailAddress()).isEqualTo("john@example.com");
            assertThat(reg.getAddress()).isEqualTo("123 Main St");
            assertThat(reg.getLogCard()).isNotNull();
            assertThat(reg.getLogCard().getFileName()).isEqualTo("log.pdf");
        }
    }

    @Nested
    class PriceCoeDetailsTests {

        @Test
        void shouldBeAnnotatedAsEmbeddable() {
            assertThat(PriceCoeDetails.class.isAnnotationPresent(Embeddable.class)).isTrue();
        }

        @Test
        void shouldSupportNoArgConstruction() {
            assertThat(new PriceCoeDetails()).isNotNull();
        }

        @Test
        void shouldStoreAllFields() {
            PriceCoeDetails price = new PriceCoeDetails();
            price.setAskingPrice(new BigDecimal("50000.00"));
            price.setCoeRenewal(LocalDate.of(2030, 6, 1));
            price.setCoeAmount(new BigDecimal("40000.00"));
            price.setDownPayment(new BigDecimal("10000.00"));
            price.setMinimumParf(new BigDecimal("5000.00"));
            price.setDepreciation(new BigDecimal("8000.00"));
            price.setRoadTax(new BigDecimal("1500.00"));
            price.setOmv(new BigDecimal("25000.00"));

            assertThat(price.getAskingPrice()).isEqualByComparingTo("50000.00");
            assertThat(price.getCoeRenewal()).isEqualTo(LocalDate.of(2030, 6, 1));
            assertThat(price.getCoeAmount()).isEqualByComparingTo("40000.00");
            assertThat(price.getDownPayment()).isEqualByComparingTo("10000.00");
            assertThat(price.getMinimumParf()).isEqualByComparingTo("5000.00");
            assertThat(price.getDepreciation()).isEqualByComparingTo("8000.00");
            assertThat(price.getRoadTax()).isEqualByComparingTo("1500.00");
            assertThat(price.getOmv()).isEqualByComparingTo("25000.00");
        }
    }

    @Nested
    class OwnershipInfoTests {

        @Test
        void shouldBeAnnotatedAsEmbeddable() {
            assertThat(OwnershipInfo.class.isAnnotationPresent(Embeddable.class)).isTrue();
        }

        @Test
        void shouldSupportNoArgConstruction() {
            assertThat(new OwnershipInfo()).isNotNull();
        }

        @Test
        void shouldStoreAllFields() {
            OwnershipInfo info = new OwnershipInfo();
            info.setRegisteredOwner("Jane Doe");
            info.setContactNumber("98765432");
            info.setEmail("jane@example.com");
            info.setAddress("456 Oak Ave");
            info.setIsSeller(true);

            assertThat(info.getRegisteredOwner()).isEqualTo("Jane Doe");
            assertThat(info.getContactNumber()).isEqualTo("98765432");
            assertThat(info.getEmail()).isEqualTo("jane@example.com");
            assertThat(info.getAddress()).isEqualTo("456 Oak Ave");
            assertThat(info.getIsSeller()).isTrue();
        }
    }

    @Nested
    class SellerInfoTests {

        @Test
        void shouldBeAnnotatedAsEmbeddable() {
            assertThat(SellerInfo.class.isAnnotationPresent(Embeddable.class)).isTrue();
        }

        @Test
        void shouldSupportNoArgConstruction() {
            assertThat(new SellerInfo()).isNotNull();
        }

        @Test
        void shouldStoreAllFields() {
            FileAttachment loa = new FileAttachment("https://example.com/loa.pdf", "loa.pdf", "application/pdf", 512L);

            SellerInfo seller = new SellerInfo();
            seller.setName("Acme Cars");
            seller.setContactNumber("61234567");
            seller.setWhatsAppNumber("61234567");
            seller.setCompanyAddress("789 Business Rd");
            seller.setEmail("info@acme.com");
            seller.setLetterOfAuthorization(loa);

            assertThat(seller.getName()).isEqualTo("Acme Cars");
            assertThat(seller.getContactNumber()).isEqualTo("61234567");
            assertThat(seller.getWhatsAppNumber()).isEqualTo("61234567");
            assertThat(seller.getCompanyAddress()).isEqualTo("789 Business Rd");
            assertThat(seller.getEmail()).isEqualTo("info@acme.com");
            assertThat(seller.getLetterOfAuthorization()).isNotNull();
            assertThat(seller.getLetterOfAuthorization().getFileName()).isEqualTo("loa.pdf");
        }
    }
}
