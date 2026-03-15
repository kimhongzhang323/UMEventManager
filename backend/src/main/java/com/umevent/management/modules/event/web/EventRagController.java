package com.umevent.management.modules.event.web;

import com.umevent.management.modules.event.application.EventRagService;
import com.umevent.management.modules.event.dto.EventPlanRequest;
import com.umevent.management.modules.event.dto.EventPlanResponse;
import com.umevent.management.modules.event.dto.HistoricalEventPattern;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events/rag")
public class EventRagController {

    private final EventRagService eventRagService;

    public EventRagController(EventRagService eventRagService) {
        this.eventRagService = eventRagService;
    }

    @GetMapping("/patterns")
    public ResponseEntity<List<HistoricalEventPattern>> patterns() {
        return ResponseEntity.ok(eventRagService.getPatterns());
    }

    @PostMapping("/plan")
    public ResponseEntity<EventPlanResponse> plan(@Valid @RequestBody EventPlanRequest request) {
        return ResponseEntity.ok(eventRagService.generatePlan(request.eventName(), request.currentMonth()));
    }
}
