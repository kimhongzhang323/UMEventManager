package com.umevent.management.shared.api;

import java.time.Instant;

public record ApiResponse<T>(
    String code,
    String message,
    T data,
    Instant timestamp,
    String traceId
) {
}
