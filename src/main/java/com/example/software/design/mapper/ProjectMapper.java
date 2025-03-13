package com.example.software.design.mapper;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper implements Mapper<ReadProject, WriteProject, Project> {

    @Override
    public ReadProject mapRead(Project from) {
        return ReadProject.builder()
                .id(from.getId())
                .name(from.getName())
                .build();
    }

    @Override
    public Project mapEntity(WriteProject from) {
        return Project.builder()
                .name(from.getName())
                .build();
    }
}
