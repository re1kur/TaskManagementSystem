package com.example.software.design.dto.task;

import com.example.software.design.dto.project.ProjectDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class TaskDto {
    int id;
    ProjectDto project;
    String name;
    String description;
    LocalDate startDate;
    LocalDate deadDate;
    String status;
}
