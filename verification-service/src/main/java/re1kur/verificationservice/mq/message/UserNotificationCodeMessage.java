package re1kur.verificationservice.mq.message;

import java.io.Serializable;

public record UserNotificationCodeMessage(String email, String code) implements Serializable {
}
