package com.vonnue.grab_resale.config.filter;

import java.io.IOException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final String TRACE_ID_HEADER = "x-trace-id";
    private static final String TRACE_ID_MDC_KEY = "traceId";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator") || path.startsWith("/swagger") || path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }

        MDC.put(TRACE_ID_MDC_KEY, traceId);
        response.setHeader(TRACE_ID_HEADER, traceId);

        String queryString = request.getQueryString();
        log.info(">>> {} {} {}", request.getMethod(), request.getRequestURI(),
                queryString != null ? "?" + queryString : "");

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("<<< {} {}ms", response.getStatus(), duration);
            MDC.clear();
        }
    }
}
