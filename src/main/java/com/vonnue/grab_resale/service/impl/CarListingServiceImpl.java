package com.vonnue.grab_resale.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vonnue.grab_resale.exception.BadRequestException;
import com.vonnue.grab_resale.exception.ResourceNotFoundException;
import com.vonnue.grab_resale.persistence.embeddable.FileAttachment;
import com.vonnue.grab_resale.persistence.embeddable.OwnershipInfo;
import com.vonnue.grab_resale.persistence.embeddable.PriceCoeDetails;
import com.vonnue.grab_resale.persistence.embeddable.RegistrationOwnership;
import com.vonnue.grab_resale.persistence.embeddable.SellerInfo;
import com.vonnue.grab_resale.persistence.embeddable.TechnicalSpecification;
import com.vonnue.grab_resale.persistence.entity.BaseEntity;
import com.vonnue.grab_resale.persistence.entity.CarImage;
import com.vonnue.grab_resale.persistence.entity.CarListing;
import com.vonnue.grab_resale.persistence.entity.CarMake;
import com.vonnue.grab_resale.persistence.entity.CarModel;
import com.vonnue.grab_resale.persistence.entity.ListingAttribute;
import com.vonnue.grab_resale.persistence.entity.User;
import com.vonnue.grab_resale.persistence.repository.CarImageRepository;
import com.vonnue.grab_resale.persistence.repository.CarListingRepository;
import com.vonnue.grab_resale.persistence.repository.CarMakeRepository;
import com.vonnue.grab_resale.persistence.repository.CarModelRepository;
import com.vonnue.grab_resale.persistence.repository.ListingAttributeRepository;
import com.vonnue.grab_resale.persistence.repository.UserRepository;
import com.vonnue.grab_resale.service.CarListingService;
import com.vonnue.grab_resale.web.dto.PageResponse;
import com.vonnue.grab_resale.web.dto.listing.CarDetailsRequest;
import com.vonnue.grab_resale.web.dto.listing.CarListingResponse;
import com.vonnue.grab_resale.web.dto.listing.CarListingSummaryResponse;
import com.vonnue.grab_resale.web.dto.listing.CreateCarListingRequest;
import com.vonnue.grab_resale.web.dto.listing.FileAttachmentRequest;
import com.vonnue.grab_resale.web.dto.listing.OwnershipInfoRequest;
import com.vonnue.grab_resale.web.dto.listing.PriceCoeDetailsRequest;
import com.vonnue.grab_resale.web.dto.listing.RegistrationOwnershipRequest;
import com.vonnue.grab_resale.web.dto.listing.SellerDetailsRequest;
import com.vonnue.grab_resale.web.dto.listing.SellerInfoRequest;
import com.vonnue.grab_resale.web.dto.listing.TechnicalSpecificationRequest;
import com.vonnue.grab_resale.web.dto.listing.UpdateCarListingRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarListingServiceImpl implements CarListingService {

    private final CarListingRepository carListingRepository;
    private final CarImageRepository carImageRepository;
    private final ListingAttributeRepository listingAttributeRepository;
    private final UserRepository userRepository;
    private final CarMakeRepository carMakeRepository;
    private final CarModelRepository carModelRepository;

    @Override
    @Transactional
    public CarListingResponse createListing(CreateCarListingRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));

        CarDetailsRequest carDetails = request.carDetails();

        CarMake make = carMakeRepository.findById(carDetails.makeId())
                .orElseThrow(() -> new ResourceNotFoundException("CarMake", carDetails.makeId()));
        CarModel model = carModelRepository.findById(carDetails.modelId())
                .orElseThrow(() -> new ResourceNotFoundException("CarModel", carDetails.modelId()));

        if (!model.getMake().getId().equals(make.getId())) {
            throw new BadRequestException("Model does not belong to the specified make");
        }

        CarListing listing = new CarListing();
        listing.setUser(user);
        listing.setMake(make);
        listing.setModel(model);
        listing.setCarCondition(carDetails.carCondition());
        listing.setYearOfManufacturing(carDetails.yearOfManufacturing());
        listing.setPlateNumber(carDetails.plateNumber());
        listing.setMileage(carDetails.mileage());
        listing.setCarType(carDetails.carType());
        listing.setColor(carDetails.color());

        listing.setTechnicalSpecification(mapTechnicalSpecification(carDetails.technicalSpecification()));

        if (carDetails.registrationOwnership() != null) {
            listing.setRegistrationOwnership(mapRegistrationOwnership(carDetails.registrationOwnership()));
        }

        if (carDetails.priceCoeDetails() != null) {
            listing.setPriceCoeDetails(mapPriceCoeDetails(carDetails.priceCoeDetails()));
        }

        SellerDetailsRequest sellerDetails = request.sellerDetails();
        listing.setSellerType(sellerDetails.sellerType());
        listing.setOwnershipInfo(mapOwnershipInfo(sellerDetails.ownershipInfo()));

        if (sellerDetails.sellerInfo() != null) {
            listing.setSellerInfo(mapSellerInfo(sellerDetails.sellerInfo()));
        }

        listing = carListingRepository.save(listing);

        saveAttributes(listing, request.otherDetails());

        List<ListingAttribute> attributes = request.otherDetails() != null
                ? listingAttributeRepository.findByListingId(listing.getId())
                : Collections.emptyList();

        return CarListingResponse.from(listing, Collections.emptyList(), attributes);
    }

    @Override
    @Transactional(readOnly = true)
    public CarListingResponse getListing(Long id) {
        CarListing listing = carListingRepository.findWithMakeAndModelById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CarListing", id));

        List<CarImage> images = carImageRepository.findByListingId(id);
        List<ListingAttribute> attributes = listingAttributeRepository.findByListingId(id);

        return CarListingResponse.from(listing, images, attributes);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CarListingSummaryResponse> getListings(Pageable pageable) {
        Page<CarListing> listings = carListingRepository.findAllBy(pageable);

        List<Long> listingIds = listings.getContent().stream()
                .map(BaseEntity::getId)
                .toList();

        Map<Long, List<CarImage>> imagesByListingId = listingIds.isEmpty()
                ? Collections.emptyMap()
                : carImageRepository.findByListingIdIn(listingIds).stream()
                        .collect(Collectors.groupingBy(img -> img.getListing().getId()));

        Page<CarListingSummaryResponse> page = listings.map(listing -> {
            List<CarImage> images = imagesByListingId.getOrDefault(listing.getId(), Collections.emptyList());
            String primaryImageUrl = images.isEmpty() ? null : images.getFirst().getImageUrl();
            return CarListingSummaryResponse.from(listing, primaryImageUrl);
        });

        return PageResponse.of(page);
    }

    @Override
    @Transactional
    public CarListingResponse updateListing(Long id, UpdateCarListingRequest request) {
        CarListing listing = carListingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CarListing", id));

        applyUpdates(listing, request);

        listing = carListingRepository.save(listing);

        if (request.otherDetails() != null) {
            listingAttributeRepository.deleteByListingId(id);
            saveAttributes(listing, request.otherDetails());
        }

        List<CarImage> images = carImageRepository.findByListingId(id);
        List<ListingAttribute> attributes = listingAttributeRepository.findByListingId(id);

        return CarListingResponse.from(listing, images, attributes);
    }

    @Override
    @Transactional
    public void deleteListing(Long id) {
        CarListing listing = carListingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CarListing", id));

        listingAttributeRepository.deleteByListingId(id);
        carImageRepository.deleteByListingId(id);
        carListingRepository.delete(listing);
    }

    private TechnicalSpecification mapTechnicalSpecification(TechnicalSpecificationRequest req) {
        TechnicalSpecification spec = new TechnicalSpecification();
        spec.setEngineNumber(req.engineNumber());
        spec.setChassisNumber(req.chassisNumber());
        spec.setFuelType(req.fuelType());
        spec.setTransmission(req.transmission());
        spec.setEngineCapacity(req.engineCapacity());
        spec.setHorsePower(req.horsePower());
        return spec;
    }

    private RegistrationOwnership mapRegistrationOwnership(RegistrationOwnershipRequest req) {
        RegistrationOwnership reg = new RegistrationOwnership();
        reg.setOwnerName(req.ownerName());
        reg.setRegistrationDate(req.registrationDate());
        reg.setNumberOfTransfers(req.numberOfTransfers());
        reg.setContactNumber(req.contactNumber());
        reg.setEmailAddress(req.emailAddress());
        reg.setAddress(req.address());
        if (req.logCard() != null) {
            reg.setLogCard(mapFileAttachment(req.logCard()));
        }
        return reg;
    }

    private PriceCoeDetails mapPriceCoeDetails(PriceCoeDetailsRequest req) {
        PriceCoeDetails details = new PriceCoeDetails();
        details.setAskingPrice(req.askingPrice());
        details.setCoeRenewal(req.coeRenewal());
        details.setCoeAmount(req.coeAmount());
        details.setDownPayment(req.downPayment());
        details.setMinimumParf(req.minimumParf());
        details.setDepreciation(req.depreciation());
        details.setRoadTax(req.roadTax());
        details.setOmv(req.omv());
        return details;
    }

    private OwnershipInfo mapOwnershipInfo(OwnershipInfoRequest req) {
        OwnershipInfo info = new OwnershipInfo();
        info.setRegisteredOwner(req.registeredOwner());
        info.setContactNumber(req.contactNumber());
        info.setEmail(req.email());
        info.setAddress(req.address());
        info.setSeller(req.isSeller());
        return info;
    }

    private SellerInfo mapSellerInfo(SellerInfoRequest req) {
        SellerInfo info = new SellerInfo();
        info.setName(req.name());
        info.setContactNumber(req.contactNumber());
        info.setWhatsAppNumber(req.whatsAppNumber());
        info.setCompanyAddress(req.companyAddress());
        info.setEmail(req.email());
        if (req.letterOfAuthorization() != null) {
            info.setLetterOfAuthorization(mapFileAttachment(req.letterOfAuthorization()));
        }
        return info;
    }

    private FileAttachment mapFileAttachment(FileAttachmentRequest req) {
        return new FileAttachment(req.fileUrl(), req.fileName(), req.mimeType(), req.size());
    }

    private void applyUpdates(CarListing listing, UpdateCarListingRequest req) {
        if (req.carCondition() != null) listing.setCarCondition(req.carCondition());
        if (req.makeId() != null) {
            CarMake make = carMakeRepository.findById(req.makeId())
                    .orElseThrow(() -> new ResourceNotFoundException("CarMake", req.makeId()));
            listing.setMake(make);
        }
        if (req.modelId() != null) {
            CarModel model = carModelRepository.findById(req.modelId())
                    .orElseThrow(() -> new ResourceNotFoundException("CarModel", req.modelId()));
            CarMake effectiveMake = req.makeId() != null ? listing.getMake() : listing.getMake();
            if (!model.getMake().getId().equals(effectiveMake.getId())) {
                throw new BadRequestException("Model does not belong to the specified make");
            }
            listing.setModel(model);
        }
        if (req.yearOfManufacturing() != null) listing.setYearOfManufacturing(req.yearOfManufacturing());
        if (req.plateNumber() != null) listing.setPlateNumber(req.plateNumber());
        if (req.mileage() != null) listing.setMileage(req.mileage());
        if (req.carType() != null) listing.setCarType(req.carType());
        if (req.color() != null) listing.setColor(req.color());

        // Technical specification
        if (req.engineNumber() != null || req.chassisNumber() != null || req.fuelType() != null
                || req.transmission() != null || req.engineCapacity() != null || req.horsePower() != null) {
            TechnicalSpecification spec = getTechnicalSpecification(listing, req);
            listing.setTechnicalSpecification(spec);
        }

        // Registration & ownership
        if (req.regOwnerName() != null || req.regRegistrationDate() != null || req.regNumberOfTransfers() != null
                || req.regContactNumber() != null || req.regEmailAddress() != null || req.regAddress() != null
                || req.regLogCard() != null) {
            RegistrationOwnership reg = listing.getRegistrationOwnership();
            if (reg == null) reg = new RegistrationOwnership();
            if (req.regOwnerName() != null) reg.setOwnerName(req.regOwnerName());
            if (req.regRegistrationDate() != null) reg.setRegistrationDate(req.regRegistrationDate());
            if (req.regNumberOfTransfers() != null) reg.setNumberOfTransfers(req.regNumberOfTransfers());
            if (req.regContactNumber() != null) reg.setContactNumber(req.regContactNumber());
            if (req.regEmailAddress() != null) reg.setEmailAddress(req.regEmailAddress());
            if (req.regAddress() != null) reg.setAddress(req.regAddress());
            if (req.regLogCard() != null) reg.setLogCard(mapFileAttachment(req.regLogCard()));
            listing.setRegistrationOwnership(reg);
        }

        // Price & COE details
        if (req.askingPrice() != null || req.coeRenewal() != null || req.coeAmount() != null
                || req.downPayment() != null || req.minimumParf() != null || req.depreciation() != null
                || req.roadTax() != null || req.omv() != null) {
            PriceCoeDetails price = listing.getPriceCoeDetails();
            if (price == null) price = new PriceCoeDetails();
            if (req.askingPrice() != null) price.setAskingPrice(req.askingPrice());
            if (req.coeRenewal() != null) price.setCoeRenewal(req.coeRenewal());
            if (req.coeAmount() != null) price.setCoeAmount(req.coeAmount());
            if (req.downPayment() != null) price.setDownPayment(req.downPayment());
            if (req.minimumParf() != null) price.setMinimumParf(req.minimumParf());
            if (req.depreciation() != null) price.setDepreciation(req.depreciation());
            if (req.roadTax() != null) price.setRoadTax(req.roadTax());
            if (req.omv() != null) price.setOmv(req.omv());
            listing.setPriceCoeDetails(price);
        }

        // Seller type
        if (req.sellerType() != null) listing.setSellerType(req.sellerType());

        // Ownership info
        if (req.ownerRegisteredOwner() != null || req.ownerContactNumber() != null || req.ownerEmail() != null
                || req.ownerAddress() != null || req.ownerIsSeller() != null) {
            OwnershipInfo ownership = listing.getOwnershipInfo();
            if (ownership == null) ownership = new OwnershipInfo();
            if (req.ownerRegisteredOwner() != null) ownership.setRegisteredOwner(req.ownerRegisteredOwner());
            if (req.ownerContactNumber() != null) ownership.setContactNumber(req.ownerContactNumber());
            if (req.ownerEmail() != null) ownership.setEmail(req.ownerEmail());
            if (req.ownerAddress() != null) ownership.setAddress(req.ownerAddress());
            if (req.ownerIsSeller() != null) ownership.setSeller(req.ownerIsSeller());
            listing.setOwnershipInfo(ownership);
        }

        // Seller info
        if (req.sellerName() != null || req.sellerContactNumber() != null || req.sellerWhatsAppNumber() != null
                || req.sellerCompanyAddress() != null || req.sellerEmail() != null
                || req.sellerLetterOfAuthorization() != null) {
            SellerInfo seller = listing.getSellerInfo();
            if (seller == null) seller = new SellerInfo();
            if (req.sellerName() != null) seller.setName(req.sellerName());
            if (req.sellerContactNumber() != null) seller.setContactNumber(req.sellerContactNumber());
            if (req.sellerWhatsAppNumber() != null) seller.setWhatsAppNumber(req.sellerWhatsAppNumber());
            if (req.sellerCompanyAddress() != null) seller.setCompanyAddress(req.sellerCompanyAddress());
            if (req.sellerEmail() != null) seller.setEmail(req.sellerEmail());
            if (req.sellerLetterOfAuthorization() != null) {
                seller.setLetterOfAuthorization(mapFileAttachment(req.sellerLetterOfAuthorization()));
            }
            listing.setSellerInfo(seller);
        }
    }

    private static @NonNull TechnicalSpecification getTechnicalSpecification(CarListing listing, UpdateCarListingRequest req) {
        TechnicalSpecification spec = listing.getTechnicalSpecification();
        if (spec == null) spec = new TechnicalSpecification();
        if (req.engineNumber() != null) spec.setEngineNumber(req.engineNumber());
        if (req.chassisNumber() != null) spec.setChassisNumber(req.chassisNumber());
        if (req.fuelType() != null) spec.setFuelType(req.fuelType());
        if (req.transmission() != null) spec.setTransmission(req.transmission());
        if (req.engineCapacity() != null) spec.setEngineCapacity(req.engineCapacity());
        if (req.horsePower() != null) spec.setHorsePower(req.horsePower());
        return spec;
    }

    private void saveAttributes(CarListing listing, Map<String, String> otherDetails) {
        if (otherDetails == null || otherDetails.isEmpty()) return;

        List<ListingAttribute> attributes = otherDetails.entrySet().stream()
                .map(entry -> {
                    ListingAttribute attr = new ListingAttribute();
                    attr.setListing(listing);
                    attr.setAttributeKey(entry.getKey());
                    attr.setAttributeValue(entry.getValue());
                    return attr;
                })
                .toList();

        listingAttributeRepository.saveAll(attributes);
    }
}
