package com.example.software.design.service;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.entity.Project;
import com.example.software.design.entity.User;
import com.example.software.design.mapper.ProjectMapper;
import com.example.software.design.repository.ProjectRepository;
import com.example.software.design.repository.UserRepository;
import com.example.software.design.util.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProjectService {

    private static final Logger logger = Logger.getLogger(ProjectService.class.getName());
    private final ProjectRepository repo;
    private final ProjectMapper mapper;
    private final UserRepository userRepo;

    @Autowired
    public ProjectService(
            ProjectRepository repo,
            ProjectMapper mapper,
            UserRepository userRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.userRepo = userRepo;
    }

    @Transactional
    public void saveProject(WriteProject writeProject) {
        Project project = mapper.mapEntity(writeProject);
        repo.save(project);
        repo.flush();
    }

    public List<ReadProject> readAllProjects() {
        return repo.findAll().stream().map(mapper::mapRead).toList();
    }

    public Optional<ReadProject> readProject(int id) {
        Optional<Project> mayBeProject = repo.findById(id);
        return mayBeProject.isPresent() ? mayBeProject.map(mapper::mapRead) : Optional.empty();
    }

    @Transactional
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
