package re1kur.verificationservice.mapper;

import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.mq.message.MessageRequest;

public interface CodeMapper {

    MessageRequest message(Code code);
}
