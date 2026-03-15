package com.umevent.management.modules.analytics.dto;

public record DepartmentRankingItem(
    int rank,
    String departmentId,
    String departmentName,
    double score,
    double scoreDelta
) {
}
