package com.example.software.design.mapper.impl;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.UserDto;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserMapper implements UserMapper {
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public DefaultUserMapper(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public ReadUser read(UserDto from) {
        return ReadUser.builder()
                .id(from.getId())
                .email(from.getEmail())
                .username(from.getUsername())
                .role(from.getRole())
                .isOauth(from.isOauth())
                .build();
    }

    public WriteUser encode(WriteUser from) {
        from.setPassword(encoder.encode(from.getPassword()));
        return from;
    }
}
