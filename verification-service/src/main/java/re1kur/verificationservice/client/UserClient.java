package re1kur.verificationservice.client;

import re1kur.verificationservice.mq.message.UserCheckResponseMessage;

public interface UserClient {
    UserCheckResponseMessage checkUser(String email);
}
