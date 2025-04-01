package com.example.software.design.controller;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.service.ProjectService;
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
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping("create")
    public void writeProject(@Validated @RequestBody WriteProject project, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) service.write(project);
        else throw new ValidationException(bindingResult.getAllErrors(), "Project is not valid.");
    }

    @GetMapping("{id}")
    public ResponseEntity<ReadProject> findProjectById(@PathVariable int id) {
        Optional<ReadProject> mayBeProject = service.read(id);
        return mayBeProject.map(readProject ->
                ResponseEntity.ok().body(readProject)).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @Transactional
    @PostMapping("{id}/attachUser")
    public ResponseEntity<String> attachUser(@PathVariable("id") int projectId, @RequestParam(name = "id") int userId) {
        return service.attachUser(projectId, userId);
    }

    @GetMapping("list")
    public List<ReadProject> findAllProjects() {
        return service.readAll();
    }

}
