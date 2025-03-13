package com.example.software.design.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WriteUser {
    @Email
    @Size(max = 256)
    @NotNull
    String email;

    @NotBlank
    @Size(min = 6, max = 64)
    @NotNull
    String username;

    @NotBlank
    @Size(min = 6, max = 128)
    @NotNull
    String password;

    boolean isOauth = false;
}
