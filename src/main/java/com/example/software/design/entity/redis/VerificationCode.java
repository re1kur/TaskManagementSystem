package com.example.software.design.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("verify_code")
public class VerificationCode implements Serializable {
    @Id
    private String email;
    private String code;
    private LocalDateTime expires;

    public boolean isExpires() {
        return expires.isBefore(LocalDateTime.now());
    }
}
