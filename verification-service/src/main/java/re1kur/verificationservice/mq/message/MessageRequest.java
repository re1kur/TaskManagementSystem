package re1kur.verificationservice.mq.message;

import java.io.Serializable;

public record MessageRequest(String email, String subject, String content) implements Serializable {
}
