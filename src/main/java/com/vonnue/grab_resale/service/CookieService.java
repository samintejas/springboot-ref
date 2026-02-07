package com.vonnue.grab_resale.service;

import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {

    void addAccessTokenCookie(HttpServletResponse response, String token);

    void addRefreshTokenCookie(HttpServletResponse response, String token);

    void clearCookies(HttpServletResponse response);
}
