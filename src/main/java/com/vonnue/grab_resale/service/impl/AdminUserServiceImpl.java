package com.vonnue.grab_resale.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vonnue.grab_resale.common.constants.Role;
import com.vonnue.grab_resale.exception.BadRequestException;
import com.vonnue.grab_resale.persistence.entity.User;
import com.vonnue.grab_resale.persistence.repository.UserRepository;
import com.vonnue.grab_resale.service.AdminUserService;
import com.vonnue.grab_resale.web.dto.admin.InviteAdminRequest;
import com.vonnue.grab_resale.web.dto.admin.InviteAdminResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public InviteAdminResponse inviteAdmin(InviteAdminRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email is already registered");
        }

        String inviteToken = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plus(48, ChronoUnit.HOURS);

        User user = new User(request.email(), request.name(), Role.ADMIN, inviteToken, expiry);
        user = userRepository.save(user);

        return InviteAdminResponse.from(user);
    }
}
