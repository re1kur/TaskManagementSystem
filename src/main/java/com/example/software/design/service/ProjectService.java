package com.example.software.design.service;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.util.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    void saveProject(WriteProject writeProject);

    List<ReadProject> readAllProjects();

    Optional<ReadProject> readProject(int id);

    boolean attachUser(int id, int userId) throws ValidationException;
}
