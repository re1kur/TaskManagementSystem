package com.example.software.design.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DatabasesConfiguration {
    @Value("${custom.databaseService.url}")
    private String databaseUrl;

    @Bean
    WebClient databaseClient() {
        return WebClient.builder()
                .baseUrl(databaseUrl)
                .build();
    }
}
