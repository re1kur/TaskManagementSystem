package com.example.software.design.service;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.WriteTask;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<ReadTask> read(int id);

    void write(WriteTask writeTask);

    List<ReadTask> readAll();
}
