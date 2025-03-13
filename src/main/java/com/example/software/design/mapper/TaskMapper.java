package com.example.software.design.mapper;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.WriteTask;
import com.example.software.design.entity.Task;
import com.example.software.design.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TaskMapper implements Mapper<ReadTask, WriteTask, Task> {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public TaskMapper(
            ProjectRepository projectRepository,
            ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ReadTask mapRead(Task from) {
        return ReadTask.builder()
                .id(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .project(projectMapper.mapRead(from.getProject()))
                .startDate(from.getStartDate())
                .deadDate(from.getDeadDate())
                .status(from.getStatus())
                .build();
    }

    @Override
    public Task mapEntity(WriteTask from) {
        return Task.builder()
                .project(projectRepository.getReferenceById(from.getProjectId()))
                .name(from.getName())
                .description(from.getDescription())
                .deadDate(from.getDeadDate())
                .build();
    }
}
