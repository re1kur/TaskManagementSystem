package re1kur.verificationservice.mapper;

import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.mq.message.UserNotificationCodeMessage;

public interface CodeMapper {

    UserNotificationCodeMessage message(Code code);
}
