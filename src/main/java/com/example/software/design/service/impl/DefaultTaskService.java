package com.example.software.design.service.impl;

import com.example.software.design.dto.task.ReadTask;
import com.example.software.design.dto.task.WriteTask;
import com.example.software.design.repository.impl.DatabaseTaskClient;
import com.example.software.design.service.MailService;
import com.example.software.design.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultTaskService implements TaskService {
    private final DatabaseTaskClient client;
    private final MailService mailService;

    @Autowired
    public DefaultTaskService(
            DatabaseTaskClient client,
            MailService mailService) {
        this.client = client;
        this.mailService = mailService;
    }

    @Override
    public Optional<ReadTask> read(int id) {
        return client.read(id);
    }

    @Override
    @Transactional
    public void write(WriteTask task) {
        client.save(task);
        mailService.sendTaskNotificationMail(task);
    }

    @Override
    public List<ReadTask> readAll() {
        return client.readAll();
    }
}
