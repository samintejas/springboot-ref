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
    private static final String ACCESS_COOKIE_PATH = "/api";
    private static final String REFRESH_COOKIE_PATH = "/api/v1/auth/refresh";

    private final JwtProperties jwtProperties;

    @Override
    public void addAccessTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = buildCookie(ACCESS_TOKEN_COOKIE, token,
                jwtProperties.accessTokenExpiration().getSeconds(), ACCESS_COOKIE_PATH);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Override
    public void addRefreshTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = buildCookie(REFRESH_TOKEN_COOKIE, token,
                jwtProperties.refreshTokenExpiration().getSeconds(), REFRESH_COOKIE_PATH);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Override
    public void clearCookies(HttpServletResponse response) {
        ResponseCookie accessCookie = buildCookie(ACCESS_TOKEN_COOKIE, "", 0, ACCESS_COOKIE_PATH);
        ResponseCookie refreshCookie = buildCookie(REFRESH_TOKEN_COOKIE, "", 0, REFRESH_COOKIE_PATH);
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private ResponseCookie buildCookie(String name, String value, long maxAgeSeconds, String path) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(jwtProperties.cookieSecure())
                .path(path)
                .maxAge(maxAgeSeconds)
                .sameSite("Lax")
                .build();
    }
}
