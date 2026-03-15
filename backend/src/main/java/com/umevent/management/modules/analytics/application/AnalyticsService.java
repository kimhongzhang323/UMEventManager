package com.umevent.management.modules.analytics.application;

import com.umevent.management.modules.analytics.dto.DepartmentEvaluationResponse;
import com.umevent.management.modules.analytics.dto.DepartmentRankingItem;
import com.umevent.management.modules.analytics.dto.DepartmentRankingResponse;
import com.umevent.management.modules.analytics.dto.MemberPerformanceResponse;
import com.umevent.management.modules.analytics.dto.ScoreTrendPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

    private static final Map<String, String> DEPARTMENTS = Map.of(
        "dept-sponsorship", "Sponsorship",
        "dept-operations", "Operations",
        "dept-marketing", "Marketing",
        "dept-logistics", "Logistics",
        "dept-program", "Program"
    );

    public MemberPerformanceResponse getMemberPerformance(
        String memberId,
        LocalDate fromDate,
        LocalDate toDate,
        String eventId
    ) {
        LocalDate resolvedTo = toDate == null ? LocalDate.now() : toDate;
        LocalDate resolvedFrom = fromDate == null ? resolvedTo.minusDays(30) : fromDate;

        String resolvedDepartmentId = getDepartmentForMember(memberId);
        String memberName = toTitleCase(memberId.replace('-', ' '));

        double completionRate = metric(memberId, "completion", 72, 95);
        double onTimeRate = metric(memberId, "ontime", 65, 96);
        double qualityScore = metric(memberId, "quality", 68, 97);
        double participationScore = metric(memberId, "participation", 62, 98);
        double ownershipScore = metric(memberId, "ownership", 60, 97);

        double weightedScore = round2(
            (completionRate * 0.35)
                + (onTimeRate * 0.25)
                + (qualityScore * 0.20)
                + (participationScore * 0.10)
                + (ownershipScore * 0.10)
        );

        Map<String, Double> metricBreakdown = new HashMap<>();
        metricBreakdown.put("completionRate", completionRate);
        metricBreakdown.put("onTimeRate", onTimeRate);
        metricBreakdown.put("qualityScore", qualityScore);
        metricBreakdown.put("participationScore", participationScore);
        metricBreakdown.put("ownershipScore", ownershipScore);

        List<String> recommendations = new ArrayList<>();
        if (onTimeRate < 80) {
            recommendations.add("Improve delivery predictability with weekly checkpoint commitments.");
        }
        if (participationScore < 75) {
            recommendations.add("Increase active participation in cross-committee planning sessions.");
        }
        if (qualityScore < 80) {
            recommendations.add("Add peer review before milestone handoff to improve quality consistency.");
        }
        if (recommendations.isEmpty()) {
            recommendations.add("Performance is stable; keep ownership breadth and mentorship responsibilities.");
        }

        return new MemberPerformanceResponse(
            memberId,
            memberName,
            resolvedDepartmentId,
            resolvedFrom,
            resolvedTo,
            eventId,
            weightedScore,
            metricBreakdown,
            buildTrend(memberId, weightedScore, resolvedTo),
            recommendations
        );
    }

    public DepartmentEvaluationResponse getDepartmentEvaluation(
        String departmentId,
        LocalDate fromDate,
        LocalDate toDate,
        String eventId
    ) {
        LocalDate resolvedTo = toDate == null ? LocalDate.now() : toDate;
        LocalDate resolvedFrom = fromDate == null ? resolvedTo.minusDays(30) : fromDate;

        String resolvedDepartmentName = DEPARTMENTS.getOrDefault(departmentId, toTitleCase(departmentId.replace('-', ' ')));

        double reliability = metric(departmentId, "reliability", 65, 97);
        double quality = metric(departmentId, "quality", 67, 98);
        double collaboration = metric(departmentId, "collaboration", 60, 98);
        double riskIndex = metric(departmentId, "risk", 8, 48);
        double efficiency = metric(departmentId, "efficiency", 62, 96);

        double evaluationScore = round2(
            (reliability * 0.30)
                + (quality * 0.25)
                + (collaboration * 0.20)
                + ((100 - riskIndex) * 0.15)
                + (efficiency * 0.10)
        );

        Map<String, Double> kpiBreakdown = new HashMap<>();
        kpiBreakdown.put("reliability", reliability);
        kpiBreakdown.put("quality", quality);
        kpiBreakdown.put("collaboration", collaboration);
        kpiBreakdown.put("riskIndex", riskIndex);
        kpiBreakdown.put("efficiency", efficiency);

        List<String> strengths = new ArrayList<>();
        List<String> improvements = new ArrayList<>();

        if (reliability >= 85) {
            strengths.add("Strong schedule adherence across event milestones.");
        } else {
            improvements.add("Tighten milestone ownership and deadline validation.");
        }

        if (quality >= 85) {
            strengths.add("Deliverable quality and acceptance rates are consistently high.");
        } else {
            improvements.add("Standardize quality gates before cross-team handoff.");
        }

        if (collaboration >= 82) {
            strengths.add("Cross-department collaboration is healthy and predictable.");
        } else {
            improvements.add("Improve cross-department communication cadence for dependencies.");
        }

        if (riskIndex > 30) {
            improvements.add("Reduce unresolved blockers and overdue critical tasks.");
        }

        if (strengths.isEmpty()) {
            strengths.add("Core execution capability is present and can be improved with stronger consistency.");
        }

        int percentile = (int) Math.min(99, Math.max(40, Math.round(evaluationScore)));
        int committeeSize = 8 + Math.abs(hash(departmentId, "size") % 22);

        return new DepartmentEvaluationResponse(
            departmentId,
            resolvedDepartmentName,
            resolvedFrom,
            resolvedTo,
            eventId,
            evaluationScore,
            kpiBreakdown,
            percentile,
            riskLabel(riskIndex),
            committeeSize,
            strengths,
            improvements
        );
    }

    public DepartmentRankingResponse getDepartmentRanking(
        LocalDate fromDate,
        LocalDate toDate,
        String eventId,
        Integer limit
    ) {
        LocalDate resolvedTo = toDate == null ? LocalDate.now() : toDate;
        LocalDate resolvedFrom = fromDate == null ? resolvedTo.minusDays(30) : fromDate;
        int resolvedLimit = limit == null ? 10 : Math.max(1, Math.min(limit, 50));

        List<DepartmentRankingItem> ranked = DEPARTMENTS.entrySet().stream()
            .map(entry -> {
                String departmentId = entry.getKey();
                String departmentName = entry.getValue();
                double score = getDepartmentEvaluation(departmentId, resolvedFrom, resolvedTo, eventId).evaluationScore();
                double previousScore = round2(score - metric(departmentId, "delta", -6, 6));
                double delta = round2(score - previousScore);
                return new DepartmentRankingItem(0, departmentId, departmentName, score, delta);
            })
            .sorted(Comparator.comparingDouble(DepartmentRankingItem::score).reversed())
            .limit(resolvedLimit)
            .collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < ranked.size(); i++) {
            DepartmentRankingItem current = ranked.get(i);
            ranked.set(i, new DepartmentRankingItem(
                i + 1,
                current.departmentId(),
                current.departmentName(),
                current.score(),
                current.scoreDelta()
            ));
        }

        return new DepartmentRankingResponse(resolvedFrom, resolvedTo, eventId, ranked);
    }

    private String getDepartmentForMember(String memberId) {
        List<String> departmentIds = new ArrayList<>(DEPARTMENTS.keySet());
        int index = Math.abs(hash(memberId, "department")) % departmentIds.size();
        return departmentIds.get(index);
    }

    private List<ScoreTrendPoint> buildTrend(String memberId, double currentScore, LocalDate toDate) {
        List<ScoreTrendPoint> trend = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate month = toDate.minusMonths(i);
            double swing = (hash(memberId + month, "trend") % 7) - 3;
            double score = round2(Math.max(50, Math.min(99, currentScore + swing)));
            trend.add(new ScoreTrendPoint(month.format(MONTH_FORMAT), score));
        }
        return trend;
    }

    private double metric(String id, String salt, int min, int max) {
        int span = Math.max(1, max - min + 1);
        int value = Math.abs(hash(id, salt)) % span;
        return round2(min + value);
    }

    private int hash(String id, String salt) {
        return (id + "::" + salt).hashCode();
    }

    private String riskLabel(double riskIndex) {
        if (riskIndex >= 35) {
            return "HIGH";
        }
        if (riskIndex >= 20) {
            return "MEDIUM";
        }
        return "LOW";
    }

    private String toTitleCase(String value) {
        String[] parts = value.trim().split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (part.isBlank()) {
                continue;
            }
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(part.substring(0, 1).toUpperCase(Locale.ROOT));
            if (part.length() > 1) {
                builder.append(part.substring(1).toLowerCase(Locale.ROOT));
            }
        }
        return builder.toString();
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
