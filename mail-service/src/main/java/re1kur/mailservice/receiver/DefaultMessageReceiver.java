package re1kur.mailservice.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import re1kur.mailservice.dto.MessageRequest;
import re1kur.mailservice.service.MailService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultMessageReceiver {
    private final MailService service;
    private static final ObjectMapper serializer = new ObjectMapper();


    @RabbitListener(queues = "${rabbitmq.queues.notification-queue.name}")
    @SneakyThrows
    public void getNotificationMessage(String message) {
        MessageRequest request = serializer.readValue(message, MessageRequest.class);
        log.info("Notifying user by email : {}", request.email());
        service.notify(request);
    }

    @RabbitListener(queues = "${rabbitmq.queues.welcome-queue.name}")
    public void getWelcomeMessage(String email) {
        log.info("Received message from welcome-queue: sending mail to {}", email);
        service.welcome(email);
    }
}
