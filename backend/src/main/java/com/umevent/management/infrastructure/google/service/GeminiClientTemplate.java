package com.umevent.management.infrastructure.google.service;

import com.umevent.management.infrastructure.config.properties.GeminiConfigProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiClientTemplate {

    private final RestClient geminiRestClient;
    private final GeminiConfigProperties properties;

    public GeminiClientTemplate(RestClient geminiRestClient, GeminiConfigProperties properties) {
        this.geminiRestClient = geminiRestClient;
        this.properties = properties;
    }

    @SuppressWarnings("unchecked")
    public String generateText(String prompt) {
        if (properties.getApiKey() == null || properties.getApiKey().isBlank()) {
            throw new IllegalStateException("GEMINI_API_KEY is not configured");
        }

        Map<String, Object> request = Map.of(
            "contents", List.of(Map.of(
                "parts", List.of(Map.of("text", prompt))
            )),
            "generationConfig", Map.of(
                "temperature", properties.getTemperature(),
                "maxOutputTokens", properties.getMaxOutputTokens()
            )
        );

        Map<String, Object> response = geminiRestClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1beta/models/{model}:generateContent")
                .queryParam("key", properties.getApiKey())
                .build(properties.getModel()))
            .body(request)
            .retrieve()
            .body(Map.class);

        if (response == null || !response.containsKey("candidates")) {
            return "";
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        if (candidates.isEmpty()) {
            return "";
        }

        Map<String, Object> first = candidates.get(0);
        Map<String, Object> content = (Map<String, Object>) first.get("content");
        if (content == null) {
            return "";
        }

        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        if (parts == null || parts.isEmpty()) {
            return "";
        }

        Object text = parts.get(0).get("text");
        return text == null ? "" : text.toString();
    }
}
