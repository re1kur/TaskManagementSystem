package com.example.software.design.repository;

import com.example.software.design.dto.mail.VerificationCode;

import java.util.Optional;

public interface CodeClient {
    void save(VerificationCode code);

    Optional<VerificationCode> read(String email);

    void delete(String email);
}
