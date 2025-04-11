package re1kur.userservice.mq.sender.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        log.info("Sending user registration message: {}", message);
        template.convertAndSend(exchange, routingKey, message);
    }
}
