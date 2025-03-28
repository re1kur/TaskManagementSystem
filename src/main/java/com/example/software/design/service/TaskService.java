package com.example.software.design.service;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.WriteTask;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<ReadTask> readTask(int id);

    void saveTask(WriteTask writeTask);

    List<ReadTask> readAllTasks();
}
