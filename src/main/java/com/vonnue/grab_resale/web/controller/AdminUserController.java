package com.vonnue.grab_resale.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vonnue.grab_resale.service.AdminUserService;
import com.vonnue.grab_resale.web.dto.admin.InviteAdminRequest;
import com.vonnue.grab_resale.web.dto.admin.InviteAdminResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin - User Management", description = "Admin user invitation and management")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/invite")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Invite a new admin", description = "Sends an invitation to create an admin account")
    public InviteAdminResponse inviteAdmin(@Valid @RequestBody InviteAdminRequest request) {
        return adminUserService.inviteAdmin(request);
    }
}