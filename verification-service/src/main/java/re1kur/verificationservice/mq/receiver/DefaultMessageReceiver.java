package re1kur.verificationservice.mq.receiver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import re1kur.verificationservice.service.CodeService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMessageReceiver {
    private final CodeService service;

    @RabbitListener(queues = "${rabbitmq.queues.registrationQueue.name}")
    public void generateAndSendCode(String message) {
        log.info("Received user registration message: {}", message);
        service.generateAndSendCode(message);
    }
}
