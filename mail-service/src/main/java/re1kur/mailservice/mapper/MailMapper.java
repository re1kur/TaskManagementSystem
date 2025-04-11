package re1kur.mailservice.mapper;

import org.springframework.mail.SimpleMailMessage;
import re1kur.mailservice.dto.MessageRequest;

public interface MailMapper {
    SimpleMailMessage notificationMessage(MessageRequest message);

    SimpleMailMessage welcomeMessage(String email);
}
