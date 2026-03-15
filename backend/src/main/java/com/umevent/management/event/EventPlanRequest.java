package com.umevent.management.event;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record EventPlanRequest(
    @NotBlank String eventName,
    @Min(1) @Max(12) int currentMonth
) {
}
