package com.umevent.management.shared.util;

import com.umevent.management.shared.api.ApiResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public final class ApiResponseFactory {

    private ApiResponseFactory() {
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(
            "SUCCESS",
            message,
            data,
            Instant.now(),
            MDC.get("traceId")
        ));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
            "CREATED",
            message,
            data,
            Instant.now(),
            MDC.get("traceId")
        ));
    }
}
