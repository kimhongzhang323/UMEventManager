package com.umevent.management.ai;

import java.time.LocalDate;
import java.util.List;

public record MinutesRecord(
    String id,
    String eventName,
    String department,
    LocalDate meetingDate,
    String rawMinutes,
    String aiSummary,
    List<String> actionItems
) {
}
