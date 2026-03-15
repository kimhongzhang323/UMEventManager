package com.umevent.management.ai;

import jakarta.validation.constraints.NotBlank;

public record MinutesUpdateRequest(
    @NotBlank(message = "Minutes text is required") String rawText
) {
}
