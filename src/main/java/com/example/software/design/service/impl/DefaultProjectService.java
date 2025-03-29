package com.example.software.design.service.impl;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.entity.Project;
import com.example.software.design.entity.User;
import com.example.software.design.mapper.ProjectMapper;
import com.example.software.design.repository.postgres.ProjectRepository;
import com.example.software.design.repository.postgres.UserRepository;
import com.example.software.design.service.ProjectService;
import com.example.software.design.util.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultProjectService implements ProjectService {

    private static final Logger log = LoggerFactory.getLogger(DefaultProjectService.class);
    private final ProjectRepository repo;
    private final ProjectMapper mapper;
    private final UserRepository userRepo;

    @Autowired
    public DefaultProjectService(
            ProjectRepository repo,
            ProjectMapper mapper,
            UserRepository userRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.userRepo = userRepo;
    }

    @Transactional
    @Override
    public void saveProject(WriteProject writeProject) {
        Project project = mapper.mapEntity(writeProject);
        repo.save(project);
        repo.flush();
    }

    @Override
    public List<ReadProject> readAllProjects() {
        return repo.findAll().stream().map(mapper::mapRead).toList();
    }

    @Override
    public Optional<ReadProject> readProject(int id) {
        Optional<Project> mayBeProject = repo.findById(id);
        return mayBeProject.isPresent() ? mayBeProject.map(mapper::mapRead) : Optional.empty();
    }

    @Transactional
    @Override
    public boolean attachUser(int id, int userId) throws ValidationException {
        Optional<Project> mayBeProject = repo.findById(id);
        Optional<User> mayBeUser = userRepo.findById(userId);
        if (mayBeProject.isPresent() && mayBeUser.isPresent()) {
            Project project = mayBeProject.get();
            User user = mayBeUser.get();
            project.addUser(user);
            repo.save(project);
            return true;
        }
        return false;
    }
}
