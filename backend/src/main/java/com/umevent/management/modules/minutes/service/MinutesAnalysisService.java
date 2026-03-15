package com.umevent.management.modules.minutes.service;

import com.umevent.management.modules.minutes.dto.MinutesRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MinutesAnalysisService {

    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");
    private static final Pattern DEPT_PATTERN = Pattern.compile("department\\s*:\\s*([A-Za-z ]+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern EVENT_PATTERN = Pattern.compile("event\\s*:\\s*([A-Za-z0-9 &-]+)", Pattern.CASE_INSENSITIVE);

    private final List<MinutesRecord> records = new CopyOnWriteArrayList<>();

    public MinutesRecord analyzeAndStore(String rawText) {
        String eventName = extractOrDefault(EVENT_PATTERN, rawText, "General Student Event");
        String department = extractOrDefault(DEPT_PATTERN, rawText, "General Committee");
        LocalDate meetingDate = extractDate(rawText);

        List<String> actionItems = new ArrayList<>();
        for (String line : rawText.split("\\R")) {
            String trimmed = line.trim();
            if (trimmed.startsWith("- ") || trimmed.toLowerCase(Locale.ROOT).startsWith("todo:")) {
                actionItems.add(trimmed.replaceFirst("^-\\s*", "").replaceFirst("(?i)^todo:\\s*", ""));
            }
        }

        if (actionItems.isEmpty()) {
            actionItems.add("Review meeting notes and assign owners");
        }

        String aiSummary = "Meeting for " + eventName + " in " + department +
            " was processed. " + actionItems.size() + " action items detected.";

        MinutesRecord record = new MinutesRecord(
            UUID.randomUUID().toString(),
            eventName,
            department,
            meetingDate,
            rawText,
            aiSummary,
            actionItems
        );

        records.add(record);
        return record;
    }

    public List<MinutesRecord> getAllRecords() {
        return records;
    }

    private String extractOrDefault(Pattern pattern, String source, String fallback) {
        Matcher matcher = pattern.matcher(source);
        if (matcher.find() && matcher.groupCount() > 0) {
            return matcher.group(1).trim();
        }
        return fallback;
    }

    private LocalDate extractDate(String source) {
        Matcher matcher = DATE_PATTERN.matcher(source);
        if (matcher.find()) {
            try {
                return LocalDate.parse(matcher.group(1));
            } catch (DateTimeParseException ignored) {
                return LocalDate.now();
            }
        }
        return LocalDate.now();
    }
}
