package com.vonnue.grab_resale.config.filter;

import java.io.IOException;
import java.util.UUID;

import com.vonnue.grab_resale.common.constants.AppConstants;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class ApiLoggingFilter extends OncePerRequestFilter {


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator") || path.startsWith("/swagger") || path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        String traceId = request.getHeader(AppConstants.TRACE_ID_HEADER);
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }

        if(!traceId.matches("[a-zA-Z0-9\\-]{1,64}")) {
            log.warn("trace id is expected to be a UUID");
        }

        MDC.put(AppConstants.TRACE_ID_MDC_KEY, traceId);
        response.setHeader(AppConstants.TRACE_ID_HEADER, traceId);

        String queryString = request.getQueryString();

        log.info(">>> {} {} {}", request.getMethod(), request.getRequestURI(),
                queryString != null ? "?" + queryString : "");

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("<<< {} {}ms", response.getStatus(), duration);
            // If another filter earlier in the chain also puts MDC values , this will clear it too
            MDC.clear();
        }
    }
}
