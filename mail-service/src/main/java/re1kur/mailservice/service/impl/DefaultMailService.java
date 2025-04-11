package re1kur.mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import re1kur.mailservice.dto.MessageRequest;
import re1kur.mailservice.mapper.MailMapper;
import re1kur.mailservice.service.MailService;

@Service
@RequiredArgsConstructor
public class DefaultMailService implements MailService {
    private final MailSender sender;
    private final MailMapper mapper;

    @Override
    public void notify(MessageRequest message) {
        SimpleMailMessage plainMessage = mapper.notificationMessage(message);
        sender.send(plainMessage);
    }

    @Override
    public void welcome(String email) {
        SimpleMailMessage welcomeMessage = mapper.welcomeMessage(email);
        sender.send(welcomeMessage);
    }
}
