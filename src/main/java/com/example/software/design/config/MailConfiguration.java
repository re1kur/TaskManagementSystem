package com.example.software.design.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MailConfiguration {

    @Value("${custom.mailService.url}")
    private String mailServiceUrl;

    @Bean
    WebClient mailClient() {
        return WebClient.builder()
                .baseUrl(mailServiceUrl)
                .build();
    }
}
