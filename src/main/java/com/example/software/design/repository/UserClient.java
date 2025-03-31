package com.example.software.design.repository;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.UserDto;
import com.example.software.design.dto.user.WriteUser;

import java.util.List;
import java.util.Optional;

public interface UserClient {
    Optional<ReadUser> read(int id);
    void save(WriteUser user);

    List<ReadUser> readAll();

    Optional<UserDto> loadByEmail(String email);

    Optional<ReadUser> readByEmail(String email);

    void update(UserDto user);
}
