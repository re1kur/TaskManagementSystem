package com.example.software.design.mapper.impl;

import com.example.software.design.dto.project.ProjectDto;
import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.mapper.ProjectMapper;
import org.springframework.stereotype.Component;

@Component
public class DefaultProjectMapper implements ProjectMapper {

    @Override
    public ReadProject read(ProjectDto from) {
        return ReadProject.builder()
                .id(from.getId())
                .name(from.getName())
                .build();
    }

//    @Override
//    public Project mapEntity(WriteProject from) {
//        return Project.builder()
//                .name(from.getName())
//                .build();
//    }
}
