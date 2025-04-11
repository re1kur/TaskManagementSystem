package re1kur.userservice.mq.sender.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import re1kur.userservice.mq.sender.MessageSender;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultMessageSender implements MessageSender {
    private final RabbitTemplate template;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    @Override
    @SneakyThrows
    public void sendUserRegistrationMessage(String message) {
        log.info("Sending registered user's email in message to two queues: {}.", message);
        template.convertAndSend(exchange, routingKey, message);
    }
}
