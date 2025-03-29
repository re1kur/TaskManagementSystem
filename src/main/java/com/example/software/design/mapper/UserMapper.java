package com.example.software.design.mapper;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.entity.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<ReadUser, WriteUser, User> {
    BCryptPasswordEncoder encoder;

    @Autowired
    public UserMapper(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public ReadUser mapRead(User from) {
        return ReadUser.builder()
                .id(from.getId())
                .email(from.getEmail())
                .username(from.getUsername())
                .role(from.getRole().getAuthority())
                .isOauth(from.isOauth())
                .build();
    }

    @Override
    public User mapEntity(WriteUser from) {
        return User.builder()
                .email(from.getEmail())
                .username(from.getUsername())
                .password(from.getPassword() != null ? encoder.encode(from.getPassword()) : "")
                .isOauth(from.isOauth())
                .build();
    }
}
