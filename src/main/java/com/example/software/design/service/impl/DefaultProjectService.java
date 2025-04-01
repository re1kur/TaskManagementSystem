package com.example.software.design.service.impl;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.repository.ProjectClient;
import com.example.software.design.service.ProjectService;
import com.example.software.design.util.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultProjectService implements ProjectService {

    private final ProjectClient client;

    @Autowired
    public DefaultProjectService(
            ProjectClient databaseClient) {
        this.client = databaseClient;
    }

    @Override
    @Transactional
    public void write(WriteProject project) {
        client.save(project);
    }

    @Override
    public List<ReadProject> readAll() {
        return client.readAll();
    }

    @Override
    public Optional<ReadProject> read(int id) {
        return client.read(id);
    }

    @Transactional
    @Override
    public ResponseEntity<String> attachUser(int projectId, int userId) throws ValidationException {
        return client.attachUser(projectId, userId);
    }
}
