package re1kur.verificationservice.mapper.impl;

import org.springframework.stereotype.Component;
import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.mq.message.UserNotificationCodeMessage;
import re1kur.verificationservice.mapper.CodeMapper;

@Component
public class DefaultCodeMapper implements CodeMapper {
    @Override
    public UserNotificationCodeMessage message(Code code) {
        return new UserNotificationCodeMessage(code.getEmail(), code.getContent());
    }
}
