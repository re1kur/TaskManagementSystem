package re1kur.verificationservice.mq.sender;

import re1kur.verificationservice.mq.message.UserNotificationCodeMessage;

public interface MessageSender {
    void publishNotification(UserNotificationCodeMessage event);

    void verifyUser(String email);

}
