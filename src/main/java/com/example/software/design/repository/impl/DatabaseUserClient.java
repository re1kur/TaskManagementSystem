package com.example.software.design.repository.impl;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.UserDto;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.mapper.UserMapper;
import com.example.software.design.repository.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DatabaseUserClient implements UserClient {
    private final WebClient client;
    private final UserMapper mapper;

    @Autowired
    public DatabaseUserClient(WebClient databaseClient, UserMapper mapper) {
        this.client = databaseClient;
        this.mapper = mapper;
    }

    @Override
    public Optional<ReadUser> read(int id) {
        return client.get()
                .uri("users/read?id=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDto.class)
                .map(mapper::read)
                .blockOptional();
    }

    @Override
    public void save(WriteUser user) {
        user = mapper.encode(user);
        client.post()
                .uri("/users/save")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .doOnError(error -> log.error("Error saving user: {}", error.getMessage()))
                .block();
    }

    @Override
    public List<ReadUser> readAll() {
        return client.get()
                .uri("/users/readAll")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(UserDto.class)
                .map(mapper::read)
                .collectList()
                .block();
    }

    @Override
    public Optional<UserDto> loadByEmail(String email) {
        return client.get()
                .uri("/users/readByEmail?email=" + email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDto.class)
                .blockOptional();
    }

    @Override
    public Optional<ReadUser> readByEmail(String email) {
        return client.get()
                .uri("/users/readByEmail?email=" + email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDto.class)
                .map(mapper::read)
                .blockOptional();
    }

    @Override
    public void update(UserDto user) {
        client.post()
                .uri("/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .doOnError(error -> log.error("Error updating user: {}", error.getMessage()))
                .block();
    }
}
