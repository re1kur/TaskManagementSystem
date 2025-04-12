package re1kur.projectservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import re1kur.projectservice.dto.ProjectPayload;
import re1kur.projectservice.entity.Project;
import re1kur.projectservice.mapper.ProjectMapper;
import re1kur.projectservice.repository.ProjectRepository;
import re1kur.projectservice.service.ProjectService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultProjectService implements ProjectService {
    private final ProjectRepository repo;
    private final ProjectMapper mapper;

    @Override
    public ResponseEntity<String> findAll() {
        List<Project> projects = repo.findAll();
        String json = mapper.mapJsonList(projects);
        return ResponseEntity.ok(json);
    }

    @Override
    public void createProject(ProjectPayload payload) {
        Project project = mapper.write(payload);
        repo.save(project);
    }
}
