package re1kur.userservice.mq.message;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UserRegistrationMessage(LocalDateTime happenedAt, String email) implements Serializable {
}
