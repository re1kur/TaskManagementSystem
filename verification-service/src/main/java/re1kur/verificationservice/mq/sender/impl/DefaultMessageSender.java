package re1kur.verificationservice.mq.sender.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import re1kur.verificationservice.mq.message.UserNotificationCodeMessage;
import re1kur.verificationservice.mq.sender.MessageSender;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultMessageSender implements MessageSender {
    private final RabbitTemplate template;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queues.notificationQueue.rout-key}")
    private String notificationQueueRoutKey;

    @Value("${rabbitmq.queues.verificationQueue.rout-key}")
    private String verificationQueueRoutKey;


    @Override
    public void publishNotification(UserNotificationCodeMessage message) {
        log.info("Notification message for {} sent.", message.email());
        template.convertAndSend(exchange, notificationQueueRoutKey, message);
    }

    @Override
    public void verifyUser(String email) {
        log.info("Sending message of verifying user by email {}", email);
        template.convertAndSend(exchange, verificationQueueRoutKey, email);
    }
}
