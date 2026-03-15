package com.umevent.management.google;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/google")
public class GoogleIntegrationController {

    private final GoogleIntegrationService googleIntegrationService;

    public GoogleIntegrationController(GoogleIntegrationService googleIntegrationService) {
        this.googleIntegrationService = googleIntegrationService;
    }

    @GetMapping("/drive-items")
    public ResponseEntity<List<GoogleItem>> getDriveItems() {
        return ResponseEntity.ok(googleIntegrationService.getDriveItems());
    }

    @GetMapping("/gmail-events")
    public ResponseEntity<List<GoogleItem>> getGmailEvents() {
        return ResponseEntity.ok(googleIntegrationService.getGmailEventRecords());
    }
}
