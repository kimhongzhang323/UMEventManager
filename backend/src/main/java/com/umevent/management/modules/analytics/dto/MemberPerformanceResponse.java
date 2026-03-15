package com.umevent.management.modules.analytics.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record MemberPerformanceResponse(
    String memberId,
    String memberName,
    String departmentId,
    LocalDate fromDate,
    LocalDate toDate,
    String eventId,
    double weightedScore,
    Map<String, Double> metricBreakdown,
    List<ScoreTrendPoint> scoreTrend,
    List<String> recommendations
) {
}
