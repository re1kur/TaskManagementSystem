package com.example.software.design.service.impl;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.WriteTask;
import com.example.software.design.entity.Task;
import com.example.software.design.mapper.TaskMapper;
import com.example.software.design.repository.postgres.TaskRepository;
import com.example.software.design.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DefaultTaskService implements TaskService {
    private final TaskRepository repo;
    private final TaskMapper mapper;

    @Autowired
    public DefaultTaskService(
            TaskRepository repo,
            TaskMapper mapper
    ) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Optional<ReadTask> readTask(int id) {
        Optional<Task> mayBeUser = repo.findById(id);
        return mayBeUser.map(mapper::mapRead);
    }

    @Transactional
    @Override
    public void saveTask(WriteTask writeTask) {
        Task task = mapper.mapEntity(writeTask);
        repo.save(task);
    }

    @Override
    public List<ReadTask> readAllTasks() {
        List<ReadTask> list = repo.findAll().stream().map(mapper::mapRead).toList();
        log.info(list.toString());
        return list;
    }
}
