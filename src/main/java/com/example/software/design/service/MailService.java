package com.example.software.design.service;

import com.example.software.design.dto.task.WriteTask;

public interface MailService {

    void sendVerifyCodeMail(String email, String code);

    void sendTaskNotificationMail(WriteTask task);
}
