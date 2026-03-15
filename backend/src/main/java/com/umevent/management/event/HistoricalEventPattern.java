package com.umevent.management.event;

import java.util.Map;

public record HistoricalEventPattern(
    String eventName,
    int year,
    Map<Integer, String> monthlyMilestones
) {
}
