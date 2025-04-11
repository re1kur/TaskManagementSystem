package re1kur.verificationservice.generator.impl;

import org.springframework.stereotype.Component;
import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.generator.CodeProvider;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
public class DefaultCodeProvider implements CodeProvider {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    @Override
    public Code provide(String email) {
        return Code.builder()
                .email(email)
                .content(generateCode())
                .expirationDate(LocalDateTime.now().plusMinutes(30))
                .build();
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
