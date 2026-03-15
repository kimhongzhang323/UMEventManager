package com.umevent.management.infrastructure.config;

import com.umevent.management.infrastructure.config.properties.GeminiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GeminiConfig {

    @Bean
    public RestClient geminiRestClient(GeminiConfigProperties properties) {
        return RestClient.builder()
            .baseUrl(properties.getBaseUrl())
            .build();
    }
}
