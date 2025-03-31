package com.example.software.design.service.impl;

import com.example.software.design.dto.project.ReadProject;
import com.example.software.design.dto.project.WriteProject;
import com.example.software.design.repository.ProjectClient;
import com.example.software.design.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultProjectService implements ProjectService {

    private final ProjectClient client;

    @Autowired
    public DefaultProjectService(
            ProjectClient databaseClient) {
        this.client = databaseClient;
    }

    @Override
    @Transactional
    public void write(WriteProject project) {
        client.save(project);
    }

    @Override
    public List<ReadProject> readAll() {
        return client.readAll();
    }

    @Override
    public Optional<ReadProject> read(int id) {
        return client.read(id);
    }

//    @Transactional
//    @Override
//    public boolean attachUser(int id, int userId) throws ValidationException {
//        Optional<Project> mayBeProject = repo.findById(id);
//        Optional<User> mayBeUser = userRepo.findById(userId);
//        if (mayBeProject.isPresent() && mayBeUser.isPresent()) {
//            Project project = mayBeProject.get();
//            User user = mayBeUser.get();
//            project.addUser(user);
//            repo.save(project);
//            return true;
//        }
//        return false;
//    }
}
