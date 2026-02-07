package com.vonnue.grab_resale.service.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.vonnue.grab_resale.config.JwtProperties;
import com.vonnue.grab_resale.service.CookieService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private static final String ACCESS_TOKEN_COOKIE = "access_token";
    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";
    private static final String COOKIE_PATH = "/api";

    private final JwtProperties jwtProperties;

    @Override
    public void addAccessTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = buildCookie(ACCESS_TOKEN_COOKIE, token,
                jwtProperties.accessTokenExpiration().getSeconds());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Override
    public void addRefreshTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = buildCookie(REFRESH_TOKEN_COOKIE, token,
                jwtProperties.refreshTokenExpiration().getSeconds());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Override
    public void clearCookies(HttpServletResponse response) {
        ResponseCookie accessCookie = buildCookie(ACCESS_TOKEN_COOKIE, "", 0);
        ResponseCookie refreshCookie = buildCookie(REFRESH_TOKEN_COOKIE, "", 0);
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private ResponseCookie buildCookie(String name, String value, long maxAgeSeconds) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(jwtProperties.cookieSecure())
                .path(COOKIE_PATH)
                .maxAge(maxAgeSeconds)
                .sameSite("Lax")
                .build();
    }
}
