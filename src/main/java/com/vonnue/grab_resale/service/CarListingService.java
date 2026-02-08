package com.vonnue.grab_resale.service;

import org.springframework.data.domain.Pageable;

import com.vonnue.grab_resale.web.dto.PageResponse;
import com.vonnue.grab_resale.web.dto.listing.CarListingResponse;
import com.vonnue.grab_resale.web.dto.listing.CarListingSummaryResponse;
import com.vonnue.grab_resale.web.dto.listing.CreateCarListingRequest;
import com.vonnue.grab_resale.web.dto.listing.UpdateCarListingRequest;

public interface CarListingService {

    CarListingResponse createListing(CreateCarListingRequest request, String email);

    CarListingResponse getListing(Long id);

    PageResponse<CarListingSummaryResponse> getListings(Pageable pageable);

    CarListingResponse updateListing(Long id, UpdateCarListingRequest request);

    void deleteListing(Long id);
}
