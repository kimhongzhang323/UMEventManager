package com.umevent.management.modules.event.dto;

import java.util.Map;

public record HistoricalEventPattern(
    String eventName,
    int year,
    Map<Integer, String> monthlyMilestones
) {
}
