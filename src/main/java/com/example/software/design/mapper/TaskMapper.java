package com.example.software.design.mapper;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.TaskDto;

public interface TaskMapper {
    ReadTask read(TaskDto from);
}
