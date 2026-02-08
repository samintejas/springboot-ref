package com.vonnue.grab_resale.web.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vonnue.grab_resale.service.AdminUserService;
import com.vonnue.grab_resale.web.dto.ApiResponse;
import com.vonnue.grab_resale.web.dto.admin.InviteAdminRequest;
import com.vonnue.grab_resale.web.dto.admin.InviteAdminResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/invite")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<InviteAdminResponse> inviteAdmin(@Valid @RequestBody InviteAdminRequest request) {
        return ApiResponse.of(adminUserService.inviteAdmin(request));
    }
}
