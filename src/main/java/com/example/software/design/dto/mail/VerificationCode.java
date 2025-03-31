package com.example.software.design.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationCode implements Serializable {
    private String email;
    private String code;
    private LocalDateTime expires;

    public boolean isExpires() {
        return expires.isBefore(LocalDateTime.now());
    }
}
