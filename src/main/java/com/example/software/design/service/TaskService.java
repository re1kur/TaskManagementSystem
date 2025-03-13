package com.example.software.design.service;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.WriteTask;
import com.example.software.design.entity.Task;
import com.example.software.design.mapper.TaskMapper;
import com.example.software.design.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TaskService {
    private final TaskRepository repo;
    private final TaskMapper mapper;

    @Autowired
    public TaskService(
            TaskRepository repo,
            TaskMapper mapper
    ) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Optional<ReadTask> readTask(int id) {
        Optional<Task> mayBeUser = repo.findById(id);
        return mayBeUser.map(mapper::mapRead);
    }

    @Transactional
    public void saveTask(WriteTask writeTask) {
        Task task = mapper.mapEntity(writeTask);
        repo.save(task);
    }

    public List<ReadTask> readAllTasks() {
        List<ReadTask> list = repo.findAll().stream().map(mapper::mapRead).toList();
        log.info(list.toString());
        return list;
    }
}
