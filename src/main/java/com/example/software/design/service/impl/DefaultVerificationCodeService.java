package com.example.software.design.service.impl;

import com.example.software.design.entity.redis.VerificationCode;
import com.example.software.design.repository.redis.VerificationCodeRepository;
import com.example.software.design.service.MailService;
import com.example.software.design.service.VerificationCodeService;
import com.example.software.design.util.verify.VerificationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultVerificationCodeService implements VerificationCodeService {
    private final VerificationCodeRepository repo;
    private final VerificationCodeGenerator generator;
    private final MailService mailService;

    @Autowired
    public DefaultVerificationCodeService(
            VerificationCodeRepository repo,
            VerificationCodeGenerator generator,
            MailService mailService
    ) {
        this.repo = repo;
        this.generator = generator;
        this.mailService = mailService;
    }

    @Override
    public void writeCode(String email) {
        VerificationCode verificationCode = generator.getCode(email);
        repo.save(verificationCode);
        mailService.sendVerifyCodeMail(email, verificationCode.getCode());
    }

    @Override
    public Optional<VerificationCode> readCode(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public void deleteCode(String email) {
        repo.deleteByEmail(email);
    }

    @Override
    public void regenerateCode(String email) {
        deleteCode(email);
        VerificationCode verificationCode = generator.getCode(email);
        repo.save(verificationCode);
        mailService.sendVerifyCodeMail(email, verificationCode.getCode());
    }
}
