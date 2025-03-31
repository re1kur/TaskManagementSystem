package com.example.software.design.repository;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;

import java.util.List;
import java.util.Optional;

public interface ProjectClient {
    void save(WriteProject project);

    List<ReadProject> readAll();

    Optional<ReadProject> read(int id);
}
