package com.umevent.management.modules.minutes.dto;

import jakarta.validation.constraints.NotBlank;

public record MinutesUpdateRequest(
    @NotBlank(message = "Minutes text is required") String rawText
) {
}
