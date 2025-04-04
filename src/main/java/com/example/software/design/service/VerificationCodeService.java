package com.example.software.design.service;


import com.example.software.design.dto.mail.VerificationCode;

import java.util.Optional;

public interface VerificationCodeService {

    void write(String email);

    Optional<VerificationCode> read(String email);

    void delete(String email);

}
