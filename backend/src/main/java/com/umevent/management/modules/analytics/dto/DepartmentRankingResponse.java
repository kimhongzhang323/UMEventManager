package com.umevent.management.modules.analytics.dto;

import java.time.LocalDate;
import java.util.List;

public record DepartmentRankingResponse(
    LocalDate fromDate,
    LocalDate toDate,
    String eventId,
    List<DepartmentRankingItem> departments
) {
}
