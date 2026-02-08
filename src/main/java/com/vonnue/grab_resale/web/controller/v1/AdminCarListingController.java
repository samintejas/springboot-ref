package com.vonnue.grab_resale.web.controller.v1;

import java.security.Principal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vonnue.grab_resale.service.CarListingService;
import com.vonnue.grab_resale.web.dto.ApiResponse;
import com.vonnue.grab_resale.web.dto.PageResponse;
import com.vonnue.grab_resale.web.dto.listing.CarListingResponse;
import com.vonnue.grab_resale.web.dto.listing.CarListingSummaryResponse;
import com.vonnue.grab_resale.web.dto.listing.CreateCarListingRequest;
import com.vonnue.grab_resale.web.dto.listing.UpdateCarListingRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/listings")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCarListingController {

    private final CarListingService carListingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CarListingResponse> createListing(@Valid @RequestBody CreateCarListingRequest request,
                                                          Principal principal) {
        return ApiResponse.of(carListingService.createListing(request, principal.getName()));
    }

    @GetMapping("/{id}")
    public ApiResponse<CarListingResponse> getListing(@PathVariable Long id) {
        return ApiResponse.of(carListingService.getListing(id));
    }

    @GetMapping
    public ApiResponse<PageResponse<CarListingSummaryResponse>> getListings(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.of(carListingService.getListings(pageable));
    }

    @PutMapping("/{id}")
    public ApiResponse<CarListingResponse> updateListing(@PathVariable Long id,
                                                          @Valid @RequestBody UpdateCarListingRequest request) {
        return ApiResponse.of(carListingService.updateListing(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteListing(@PathVariable Long id) {
        carListingService.deleteListing(id);
    }
}
