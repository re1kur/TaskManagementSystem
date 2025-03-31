package com.example.software.design.mapper.impl;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.TaskDto;
import com.example.software.design.mapper.ProjectMapper;
import com.example.software.design.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultTaskMapper implements TaskMapper {
    private final ProjectMapper projectMapper;

    @Autowired
    public DefaultTaskMapper(
            DefaultProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public ReadTask read(TaskDto from) {
        return ReadTask.builder()
                .id(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .project(projectMapper.read(from.getProject()))
                .startDate(from.getStartDate())
                .deadDate(from.getDeadDate())
                .status(from.getStatus())
                .build();
    }

//    @Override
//    public Task mapEntity(WriteTask from) {
//        return Task.builder()
//                .project(projectRepository.getReferenceById(from.getProjectId()))
//                .name(from.getName())
//                .description(from.getDescription())
//                .deadDate(from.getDeadDate())
//                .build();
//    }
}
