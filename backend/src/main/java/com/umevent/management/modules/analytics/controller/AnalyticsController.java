package com.umevent.management.modules.analytics.controller;

import com.umevent.management.modules.analytics.service.AnalyticsService;
import com.umevent.management.modules.analytics.dto.DepartmentEvaluationResponse;
import com.umevent.management.modules.analytics.dto.DepartmentRankingResponse;
import com.umevent.management.modules.analytics.dto.MemberPerformanceResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping({"/api/analytics", "/v1/analytics"})
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/members/{memberId}/performance")
    public ResponseEntity<MemberPerformanceResponse> getMemberPerformance(
        @PathVariable String memberId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
        @RequestParam(required = false) String eventId
    ) {
        return ResponseEntity.ok(analyticsService.getMemberPerformance(memberId, fromDate, toDate, eventId));
    }

    @GetMapping("/departments/{departmentId}/evaluation")
    public ResponseEntity<DepartmentEvaluationResponse> getDepartmentEvaluation(
        @PathVariable String departmentId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
        @RequestParam(required = false) String eventId
    ) {
        return ResponseEntity.ok(analyticsService.getDepartmentEvaluation(departmentId, fromDate, toDate, eventId));
    }

    @GetMapping("/departments/ranking")
    public ResponseEntity<DepartmentRankingResponse> getDepartmentRanking(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
        @RequestParam(required = false) String eventId,
        @RequestParam(required = false) Integer limit
    ) {
        return ResponseEntity.ok(analyticsService.getDepartmentRanking(fromDate, toDate, eventId, limit));
    }
}
