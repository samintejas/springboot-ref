package com.vonnue.grab_resale.web.controller;

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
import com.vonnue.grab_resale.web.dto.PageResponse;
import com.vonnue.grab_resale.web.dto.listing.CarListingResponse;
import com.vonnue.grab_resale.web.dto.listing.CarListingSummaryResponse;
import com.vonnue.grab_resale.web.dto.listing.CreateCarListingRequest;
import com.vonnue.grab_resale.web.dto.listing.UpdateCarListingRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/listings")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin - Car Listings", description = "CRUD operations for car listings (admin only)")
public class AdminCarListingController {

    private final CarListingService carListingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a car listing")
    public CarListingResponse createListing(@Valid @RequestBody CreateCarListingRequest request,
                                            Principal principal) {
        return carListingService.createListing(request, principal.getName());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a car listing by ID")
    public CarListingResponse getListing(@PathVariable Long id) {
        return carListingService.getListing(id);
    }

    @GetMapping
    @Operation(summary = "List car listings", description = "Paginated list sorted by creation date descending")
    public PageResponse<CarListingSummaryResponse> getListings(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return carListingService.getListings(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a car listing", description = "Partial update — only non-null fields are applied")
    public CarListingResponse updateListing(@PathVariable Long id,
                                            @Valid @RequestBody UpdateCarListingRequest request) {
        return carListingService.updateListing(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a car listing")
    public void deleteListing(@PathVariable Long id) {
        carListingService.deleteListing(id);
    }
}
