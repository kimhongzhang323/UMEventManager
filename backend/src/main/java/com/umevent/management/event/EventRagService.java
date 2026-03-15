package com.umevent.management.event;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class EventRagService {

    private final List<HistoricalEventPattern> memoryBank = new ArrayList<>();

    public EventRagService() {
        Map<Integer, String> hackathon2024 = new HashMap<>();
        hackathon2024.put(1, "Secure sponsors and define tracks");
        hackathon2024.put(2, "Open registrations and launch social campaign");
        hackathon2024.put(3, "Confirm judges and rehearsal checklist");

        Map<Integer, String> careerFair2025 = new HashMap<>();
        careerFair2025.put(5, "Book venue and company outreach");
        careerFair2025.put(6, "Student prep workshops and CV clinic");
        careerFair2025.put(7, "Finalize booth map and volunteer shifts");

        memoryBank.add(new HistoricalEventPattern("Innovation Hackathon", 2024, hackathon2024));
        memoryBank.add(new HistoricalEventPattern("Campus Career Fair", 2025, careerFair2025));
    }

    public List<HistoricalEventPattern> getPatterns() {
        return memoryBank;
    }

    public EventPlanResponse generatePlan(String eventName, int currentMonth) {
        List<HistoricalEventPattern> retrieved = memoryBank.stream()
            .filter(pattern -> pattern.eventName().toLowerCase(Locale.ROOT).contains(eventName.toLowerCase(Locale.ROOT))
                || eventName.toLowerCase(Locale.ROOT).contains(pattern.eventName().toLowerCase(Locale.ROOT)))
            .toList();

        if (retrieved.isEmpty()) {
            retrieved = memoryBank;
        }

        List<String> memories = new ArrayList<>();
        List<String> actions = new ArrayList<>();

        for (HistoricalEventPattern pattern : retrieved) {
            String found = pattern.monthlyMilestones().getOrDefault(
                currentMonth,
                "No exact monthly match, follow preparation fundamentals"
            );
            memories.add(pattern.year() + " - " + pattern.eventName() + ": " + found);
            actions.add("Apply pattern from " + pattern.year() + " for " + eventName + ": " + found);
        }

        actions.add("Schedule weekly checkpoint for all committees");
        actions.add("Auto-generate timeline tasks for next 4 weeks");

        String styleSummary = "RAG style retrieval done. The event workflow is usually sponsor-first, then campaign, then execution prep.";
        return new EventPlanResponse(eventName, styleSummary, memories, actions);
    }
}
