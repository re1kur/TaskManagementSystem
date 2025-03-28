package com.example.software.design.util.verify;

import com.example.software.design.entity.redis.VerificationCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
public class VerificationCodeGenerator {
    @Value("${custom.verify.code.lifetime}")
    public Integer DEFAULT_LIFETIME_MINUTES = 15;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    private String generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    public VerificationCode getCode(String email) {
        LocalDateTime expires = LocalDateTime.now().plusMinutes(DEFAULT_LIFETIME_MINUTES);
        String code = generateCode();
        return VerificationCode.builder()
                .email(email)
                .code(code)
                .expires(expires)
                .build();
    }
}
