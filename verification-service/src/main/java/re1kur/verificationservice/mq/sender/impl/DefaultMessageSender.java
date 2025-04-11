package re1kur.verificationservice.mq.sender.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import re1kur.verificationservice.mq.message.MessageRequest;
import re1kur.verificationservice.mq.sender.MessageSender;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultMessageSender implements MessageSender {
    private final RabbitTemplate template;
    private static final ObjectMapper serializer = new ObjectMapper();

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queues.notificationQueue.rout-key}")
    private String notificationQueueRoutKey;

    @Value("${rabbitmq.queues.verificationQueue.rout-key}")
    private String verificationQueueRoutKey;


    @Override
    @SneakyThrows
    public void publishNotification(MessageRequest request) {
        log.info("Notification message for {} sent.", request.email());
        template.convertAndSend(exchange, notificationQueueRoutKey,
                serializer.writeValueAsString(request));
    }

    @Override
    public void verifyUser(String email) {
        log.info("Sending message of verifying user by email {}", email);
        template.convertAndSend(exchange, verificationQueueRoutKey, email);
    }
}
