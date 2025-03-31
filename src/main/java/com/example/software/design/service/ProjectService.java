package com.example.software.design.service;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    void write(WriteProject writeProject);

    List<ReadProject> readAll();

    Optional<ReadProject> read(int id);

//    boolean attachUser(int id, int userId) throws ValidationException;
}
