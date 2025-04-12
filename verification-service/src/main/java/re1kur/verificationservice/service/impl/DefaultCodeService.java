package re1kur.verificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re1kur.verificationservice.client.UserClient;
import re1kur.verificationservice.dto.UserVerificationPayload;
import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.mq.message.UserCheckResponseMessage;
import re1kur.verificationservice.mq.message.MessageRequest;
import re1kur.verificationservice.exception.VerificationException;
import re1kur.verificationservice.generator.CodeProvider;
import re1kur.verificationservice.mapper.CodeMapper;
import re1kur.verificationservice.mq.sender.MessageSender;
import re1kur.verificationservice.repository.CodeRepository;
import re1kur.verificationservice.service.CodeService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultCodeService implements CodeService {
    private final CodeProvider codeProvider;
    private final CodeRepository repo;
    private final MessageSender sender;
    private final CodeMapper mapper;
    private final UserClient client;


    @Override
    @Transactional
    public void verify(UserVerificationPayload payload) throws VerificationException {
        String email = payload.email();
        if (userIsNotExistsOrValid(email)) throw new VerificationException("User doesn't exists or already verified.");
        Code code = repo.findById(email).orElseThrow(() -> new VerificationException("Code not found."));
        if (LocalDateTime.now().isAfter(code.getExpirationDate())) {
            generateAndSendCode(email);
            throw new VerificationException("Code expired. Check the mail.");
        }
        if (code.getContent().equals(payload.code())) {
            repo.delete(code);
            sender.verifyUser(email);
            return;
        }
        throw new VerificationException("Code is incorrect.");
    }


    @Override
    @Transactional
    public void generateAndSendCode(String email) {
        Code code = codeProvider.provide(email);
        repo.save(code);
        MessageRequest notification = mapper.message(code);
        sender.publishNotification(notification);
    }

    private boolean userIsNotExistsOrValid(String email) {
        UserCheckResponseMessage resp = client.checkUser(email);
        if (!resp.isExists()) return true;
        return resp.isVerified();
    }
}
