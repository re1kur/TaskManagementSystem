package com.example.software.design.repository.impl;

import com.example.software.design.dto.project.ProjectDto;
import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.mapper.ProjectMapper;
import com.example.software.design.repository.ProjectClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class DatabaseProjectClient implements ProjectClient {
    private final WebClient client;
    private final ProjectMapper mapper;

    public DatabaseProjectClient(WebClient client,
                                 ProjectMapper mapper
                                 ) {
        this.client = client;
        this.mapper = mapper;
    }

    @Override
    public void save(WriteProject project) {
        client.post()
                .uri("/projects/save")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(project)
                .retrieve()
                .toBodilessEntity()
                .doOnError(error -> log.error("Error saving project: {}", error.getMessage()))
                .block();
    }

    @Override
    public List<ReadProject> readAll() {
        return client.get()
                .uri("/projects/readAll")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProjectDto.class)
                .map(mapper::read)
                .collectList()
                .block();
    }

    @Override
    public Optional<ReadProject> read(int id) {
        log.info("Read project with id {}", id);
        return client.get()
                .uri("/projects/read?id=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProjectDto.class)
                .map(mapper::read)
                .blockOptional();
    }

    @Override
    public ResponseEntity<String> attachUser(int projectId, int userId) {
        return client.post()
                .uri("/projects/attachUser")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("projectId", projectId, "userId", userId))
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
