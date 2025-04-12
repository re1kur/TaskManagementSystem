package re1kur.projectservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import re1kur.projectservice.dto.ProjectPayload;
import re1kur.projectservice.service.ProjectService;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;

    @GetMapping("list")
    public ResponseEntity<String> getProjects() {
        return service.findAll();
    }

    @PostMapping("create")
    public void createProject(@RequestBody @Validated ProjectPayload payload) {
        service.createProject(payload);
    }
}
