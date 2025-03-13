package com.example.software.design.dto.task;

import com.example.software.design.dto.project.ReadProject;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class ReadTask {
    int id;
    ReadProject project;
    String name;
    String description;
    LocalDate startDate;
    LocalDate deadDate;
    String status;
}
