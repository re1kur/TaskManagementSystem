package re1kur.mailservice.mapper.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import re1kur.mailservice.dto.MessageRequest;
import re1kur.mailservice.mapper.MailMapper;

@Component
public class DefaultMailMapper implements MailMapper {
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public SimpleMailMessage notificationMessage(MessageRequest message) {
        SimpleMailMessage plainMessage = new SimpleMailMessage();
        plainMessage.setFrom(from);
        plainMessage.setTo(message.email());
        plainMessage.setSubject(message.subject());
        plainMessage.setText(message.content());
        return plainMessage;
    }

    @Override
    public SimpleMailMessage welcomeMessage(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Welcome to the Task Management System");
        message.setText("Hello, " + email + "!\nSoon you'll receive " +
                        "the verification code, that you need to " +
                        "verify your email.");
        return message;
    }
}
