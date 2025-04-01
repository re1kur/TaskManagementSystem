package com.example.software.design.service;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.util.exceptions.ValidationException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    void write(WriteProject writeProject);

    List<ReadProject> readAll();

    Optional<ReadProject> read(int id);

    ResponseEntity<String> attachUser(int projectId, int userId) throws ValidationException;

}
