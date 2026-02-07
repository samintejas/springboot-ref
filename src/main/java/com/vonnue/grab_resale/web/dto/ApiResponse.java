package com.vonnue.grab_resale.web.dto;

import java.time.Instant;

import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(T data, String message, Instant timestamp, String traceId) {

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data, null, Instant.now(), MDC.get("traceId"));
    }

    public static <T> ApiResponse<T> of(T data, String message) {
        return new ApiResponse<>(data, message, Instant.now(), MDC.get("traceId"));
    }

    public static ApiResponse<Void> withMessage(String message) {
        return new ApiResponse<>(null, message, Instant.now(), MDC.get("traceId"));
    }
}
