package com.example.software.design.repository.impl;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.TaskDto;
import com.example.software.design.dto.task.WriteTask;
import com.example.software.design.mapper.TaskMapper;
import com.example.software.design.repository.TaskClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DatabaseTaskClient implements TaskClient {
    private final WebClient client;
    private final TaskMapper mapper;

    @Autowired
    public DatabaseTaskClient(WebClient databaseClient, TaskMapper mapper) {
        this.client = databaseClient;
        this.mapper = mapper;
    }

    @Override
    public Optional<ReadTask> read(int id) {
        return client.get()
                .uri("/tasks/read?id=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TaskDto.class)
                .map(mapper::read)
                .blockOptional();
    }

    @Override
    public void save(WriteTask task) {
        client.post()
                .uri("/tasks/save")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .retrieve()
                .toBodilessEntity()
                .doOnError(error -> log.error("Error saving task: {}", error.getMessage()))
                .block();
    }

    @Override
    public List<ReadTask> readAll() {
        return client.get()
                .uri("/tasks/readAll")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TaskDto.class)
                .map(mapper::read)
                .collectList()
                .block();
    }
}
