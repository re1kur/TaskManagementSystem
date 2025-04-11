package re1kur.userservice.mq.receiver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import re1kur.userservice.entity.User;
import re1kur.userservice.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultReceiver {
    private final UserRepository repo;

    @RabbitListener(queues = "${rabbitmq.queues.verification-queue.name}")
    public void verifiedUserQueue(String email) {
        User user = repo.findByEmail(email).get();
        user.setVerified(true);
        repo.save(user);
    }
}
