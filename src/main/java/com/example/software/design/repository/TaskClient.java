package com.example.software.design.repository;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.WriteTask;

import java.util.List;
import java.util.Optional;

public interface TaskClient {
    Optional<ReadTask> read(int id);

    void save(WriteTask task);

    List<ReadTask> readAll();
}
