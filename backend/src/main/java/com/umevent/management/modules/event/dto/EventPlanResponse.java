package com.umevent.management.modules.event.dto;

import java.util.List;

public record EventPlanResponse(
    String eventName,
    String styleSummary,
    List<String> retrievedMemories,
    List<String> nextActions
) {
}
