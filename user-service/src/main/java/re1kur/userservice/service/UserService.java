package re1kur.userservice.service;

import org.springframework.http.ResponseEntity;
import re1kur.userservice.dto.UserPayload;
import re1kur.userservice.exception.UserLoginException;
import re1kur.userservice.exception.UserRegistrationException;
import re1kur.userservice.mq.message.UserCheckResponseMessage;

public interface UserService {
    void register(UserPayload payload) throws UserRegistrationException;

    ResponseEntity<String> login(UserPayload payload) throws UserLoginException;

    ResponseEntity<UserCheckResponseMessage> checkUser(String email);
}
