package com.example.software.design.dto.user;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String email;
    private String username;
    private String password;
    private String role;
    private boolean isOauth;
    private boolean isVerified;
}
