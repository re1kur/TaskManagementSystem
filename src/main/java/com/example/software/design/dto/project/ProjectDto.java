package com.example.software.design.dto.project;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProjectDto {
    int id;
    String name;
}
