package com.example.software.design.util.verify;

import com.example.software.design.entity.redis.VerificationCode;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationCodeGeneratorTest {

    private static final Logger log = LoggerFactory.getLogger(VerificationCodeGeneratorTest.class);

    @Test
    public void generateVerificationCode() {
        VerificationCodeGenerator generator = new VerificationCodeGenerator();
        VerificationCode code = generator.getCode("example@mail.com");
        assertNotNull(code);
        assertEquals(10, code.getCode().length());
        log.info(code.toString());
    }
}
