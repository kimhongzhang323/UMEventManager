package com.umevent.management.modules.analytics.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record DepartmentEvaluationResponse(
    String departmentId,
    String departmentName,
    LocalDate fromDate,
    LocalDate toDate,
    String eventId,
    double evaluationScore,
    Map<String, Double> kpiBreakdown,
    int percentile,
    String riskLevel,
    int committeeSize,
    List<String> strengths,
    List<String> improvements
) {
}
