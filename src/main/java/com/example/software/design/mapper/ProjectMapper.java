package com.example.software.design.mapper;

import com.example.software.design.dto.project.ProjectDto;
import com.example.software.design.dto.project.ReadProject;

public interface ProjectMapper {

    ReadProject read(ProjectDto from);
}
