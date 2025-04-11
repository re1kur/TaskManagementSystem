package re1kur.verificationservice.service;

import re1kur.verificationservice.dto.UserVerificationPayload;
import re1kur.verificationservice.exception.VerificationException;

public interface CodeService {
    void verify(UserVerificationPayload payload) throws VerificationException;

    void generateAndSendCode(String email);
}
