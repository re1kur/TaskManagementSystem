package com.example.software.design.service;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.util.exceptions.VerificationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<ReadUser> readUser(Integer id);

    void saveUser(WriteUser writeUser);

    List<ReadUser> readAllUsers();

    Optional<ReadUser> readUserByEmail(String email);

    void updateUser(WriteUser writeUser);

    Map<String, String> getInfo(Authentication authentication);

    void verify(String email, String code) throws VerificationException;
}
