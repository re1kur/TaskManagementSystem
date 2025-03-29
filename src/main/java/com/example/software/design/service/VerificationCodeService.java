package com.example.software.design.service;


import com.example.software.design.entity.redis.VerificationCode;

import java.util.Optional;

public interface VerificationCodeService {

    void writeCode(String email);

    Optional<VerificationCode> readCode(String email);

    void deleteCode(String email);

    void regenerateCode(String email);
}
