package com.umevent.management.google;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoogleIntegrationService {

    @Value("${app.google.driveFolderId}")
    private String driveFolderId;

    @Value("${app.google.gmailQuery}")
    private String gmailQuery;

    public List<GoogleItem> getDriveItems() {
        // Placeholder records to simulate Google Drive retrieval.
        return List.of(
            new GoogleItem("drv-001", "Event Budget 2026", "drive", "https://drive.google.com/drive/folders/" + driveFolderId, LocalDateTime.now().minusDays(1)),
            new GoogleItem("drv-002", "Sponsor Deck", "drive", "https://drive.google.com/drive/folders/" + driveFolderId, LocalDateTime.now().minusHours(2))
        );
    }

    public List<GoogleItem> getGmailEventRecords() {
        // Placeholder records to simulate Gmail search retrieval.
        return List.of(
            new GoogleItem("mail-001", "Subject: Venue confirmation", "gmail", "https://mail.google.com/mail/u/0/#search/" + gmailQuery, LocalDateTime.now().minusDays(2)),
            new GoogleItem("mail-002", "Subject: Sponsorship follow-up", "gmail", "https://mail.google.com/mail/u/0/#search/" + gmailQuery, LocalDateTime.now().minusDays(3))
        );
    }
}
