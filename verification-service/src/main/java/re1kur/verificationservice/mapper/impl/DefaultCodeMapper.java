package re1kur.verificationservice.mapper.impl;

import org.springframework.stereotype.Component;
import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.mq.message.MessageRequest;
import re1kur.verificationservice.mapper.CodeMapper;

@Component
public class DefaultCodeMapper implements CodeMapper {
    @Override
    public MessageRequest message(Code code) {
        return new MessageRequest(code.getEmail(),
                "Task Management System | Verification code",
                code.getContent());
    }
}
