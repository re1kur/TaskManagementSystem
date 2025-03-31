package com.example.software.design.controller;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.WriteTask;
import com.example.software.design.service.TaskService;
import com.example.software.design.util.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<ReadTask> readTask(@PathVariable int id) {
        Optional<ReadTask> mayBeTask = service.read(id);
        return mayBeTask.map(ResponseEntity.ok()::body)
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @Transactional
    @PostMapping("create")
    public void writeTask(@Validated @RequestBody WriteTask writeTask, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) service.write(writeTask);
        else throw new ValidationException(bindingResult.getAllErrors(), "Task is not valid.");
    }

    @GetMapping("list")
    public List<ReadTask> readAllTasks() {
        return service.readAll();
    }
}
