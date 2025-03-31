package com.example.software.design.repository.impl;

import com.example.software.design.dto.mail.VerificationCode;
import com.example.software.design.repository.CodeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@Slf4j
public class DatabaseCodeClient implements CodeClient {
    private final WebClient client;

    @Autowired
    public DatabaseCodeClient(WebClient databaseClient) {
        this.client = databaseClient;
    }

    @Override
    public void save(VerificationCode code) {
        client.post()
                .uri("/code/save")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(code)
                .retrieve()
                .toBodilessEntity()
                .doOnError(error -> log.error("Error saving code: {}", error.getMessage()))
                .block();
    }

    @Override
    public Optional<VerificationCode> read(String email) {
        return client.get()
                .uri("/code/read?email=" + email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(VerificationCode.class)
                .mapNotNull(ResponseEntity::getBody)
                .blockOptional();
    }

    @Override
    public void delete(String email) {
        client.post()
                .uri("/code/delete?email=" + email)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(email)
                .retrieve();
    }
}
