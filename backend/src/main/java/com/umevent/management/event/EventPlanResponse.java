package com.umevent.management.event;

import java.util.List;

public record EventPlanResponse(
    String eventName,
    String styleSummary,
    List<String> retrievedMemories,
    List<String> nextActions
) {
}
