package re1kur.userservice.mapper;

import re1kur.userservice.dto.UserPayload;
import re1kur.userservice.entity.User;
import re1kur.userservice.mq.message.UserRegistrationMessage;

public interface UserMapper {
    User write(UserPayload payload);

    UserRegistrationMessage message(User user);
}
