package re1kur.mailservice.service;

import re1kur.mailservice.dto.MessageRequest;

public interface MailService {
    void notify(MessageRequest message);

    void welcome(String email);
}
