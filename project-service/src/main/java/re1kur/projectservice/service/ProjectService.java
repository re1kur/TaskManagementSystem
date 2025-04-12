package re1kur.projectservice.service;

import org.springframework.http.ResponseEntity;
import re1kur.projectservice.dto.ProjectPayload;

public interface ProjectService {
    ResponseEntity<String> findAll();

    void createProject(ProjectPayload payload);
}
