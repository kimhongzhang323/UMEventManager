package com.umevent.management.modules.minutes.controller;

import com.umevent.management.modules.minutes.service.MinutesAnalysisService;
import com.umevent.management.modules.minutes.dto.MinutesRecord;
import com.umevent.management.modules.minutes.dto.MinutesUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai/minutes")
public class MinutesController {

    private final MinutesAnalysisService minutesAnalysisService;

    public MinutesController(MinutesAnalysisService minutesAnalysisService) {
        this.minutesAnalysisService = minutesAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<MinutesRecord> analyze(@Valid @RequestBody MinutesUpdateRequest request) {
        return ResponseEntity.ok(minutesAnalysisService.analyzeAndStore(request.rawText()));
    }

    @GetMapping
    public ResponseEntity<List<MinutesRecord>> listRecords() {
        return ResponseEntity.ok(minutesAnalysisService.getAllRecords());
    }
}
