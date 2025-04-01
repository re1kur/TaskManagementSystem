package com.example.software.design.service.impl;

import com.example.software.design.dto.mail.EmailRequest;
import com.example.software.design.dto.task.WriteTask;
import com.example.software.design.event.ProjectChangesEvent;
import com.example.software.design.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DefaultMailService implements MailService {
    private static final Logger log = LoggerFactory.getLogger(DefaultMailService.class);
    private final RabbitTemplate rabbitTemplate;

    @Value("${custom.mailService.exchange.verification}")
    private String verificationExchange;

    @Value("${custom.mailService.routingKey.verification}")
    private String verificationRoutingKey;

    @Value("${custom.mailService.exchange.notification}")
    private String notificationExchange;

    @Value("${custom.mailService.routingKey.notification}")
    private String notificationRoutingKey;

    public DefaultMailService(
            RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendVerifyCodeMail(String email, String code) {
        EmailRequest emailRequest = EmailRequest.builder()
                .to(email)
                .subject("Verify Code")
                .body("Verify Code is " + code)
                .build();

        log.info("EmailRequest to be sent (before serialization): {}", emailRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonMessage = objectMapper.writeValueAsString(emailRequest);
            log.info("JSON message to be sent: {}", jsonMessage);
            rabbitTemplate.convertAndSend(verificationExchange, verificationRoutingKey, jsonMessage);
            log.info("Sending verification code '{}' mail to {}.", code, email);
        } catch (JsonProcessingException e) {
            log.error("Error serializing EmailRequest to JSON:", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendTaskNotificationMail(WriteTask task) {
        ProjectChangesEvent event = ProjectChangesEvent.builder()
                .objectChangesId(task.getProjectId())
                .changesBodyMessage("New task in project №%d: ".formatted(task.getProjectId()) + task)
                .changesTimestamp(LocalDateTime.now())
                .build();

        log.info("ProjectChangesEvent to be sent: {}", event);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonMessage = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, jsonMessage);
            log.info("Sending task notification mail: {}", jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Error serializing ProjectChangesEvent to JSON:", e);
            throw new RuntimeException(e);
        }
    }
}
