package com.umevent.management.modules.integration.dto;

import java.time.LocalDateTime;

public record GoogleItem(
    String id,
    String title,
    String source,
    String link,
    LocalDateTime lastUpdated
) {
}
