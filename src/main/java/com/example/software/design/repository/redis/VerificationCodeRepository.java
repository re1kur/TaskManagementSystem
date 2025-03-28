package com.example.software.design.repository.redis;

import com.example.software.design.entity.redis.VerificationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
    Optional<VerificationCode> findByEmail(String email);

    void deleteByEmail(String email);
}
