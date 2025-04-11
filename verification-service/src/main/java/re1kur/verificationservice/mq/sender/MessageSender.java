package re1kur.verificationservice.mq.sender;

import re1kur.verificationservice.mq.message.MessageRequest;

public interface MessageSender {
    void publishNotification(MessageRequest event);

    void verifyUser(String email);

}
