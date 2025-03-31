package com.example.software.design.service.impl;

import com.example.software.design.dto.mail.VerificationCode;
import com.example.software.design.repository.impl.DatabaseCodeClient;
import com.example.software.design.service.MailService;
import com.example.software.design.service.VerificationCodeService;
import com.example.software.design.util.verify.VerificationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultVerificationCodeService implements VerificationCodeService {
    private final DatabaseCodeClient client;
    private final VerificationCodeGenerator generator;
    private final MailService mailService;

    @Autowired
    public DefaultVerificationCodeService(
            DatabaseCodeClient client,
            VerificationCodeGenerator generator,
            MailService mailService
    ) {
        this.client = client;
        this.generator = generator;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public void write(String email) {
        VerificationCode verificationCode = generator.getCode(email);
        client.save(verificationCode);
        mailService.sendVerifyCodeMail(email, verificationCode.getCode());
    }

    @Override
    public Optional<VerificationCode> read(String email) {
        return client.read(email);
    }

    @Override
    public void delete(String email) {
        client.delete(email);
    }
}
