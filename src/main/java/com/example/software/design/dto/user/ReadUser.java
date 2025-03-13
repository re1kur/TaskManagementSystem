package com.example.software.design.dto.user;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ReadUser {
    int id;
    String email;
    String username;
    String role;
    boolean isOauth;
}
