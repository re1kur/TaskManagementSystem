package com.example.software.design.mapper;


import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.UserDto;
import com.example.software.design.dto.user.WriteUser;

public interface UserMapper {
    ReadUser read(UserDto from);

    WriteUser encode(WriteUser from);
}
