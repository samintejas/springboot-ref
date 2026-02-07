package com.vonnue.grab_resale.common;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Optional<String> getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return Optional.empty();
        }
        return Optional.of(auth.getName());
    }

    public static String requireCurrentUsername() {
        return getCurrentUsername()
                .orElseThrow(() -> new IllegalStateException("No authenticated user in security context"));
    }
}
