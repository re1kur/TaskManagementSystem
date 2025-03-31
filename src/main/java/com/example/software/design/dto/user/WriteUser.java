package com.example.software.design.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WriteUser {
    @Email
    @Size(max = 256)
    @NotNull
    private String email;

    @NotBlank
    @Size(min = 6, max = 64)
    @NotNull
    private String username;

    @NotBlank
    @Size(min = 6, max = 128)
    @NotNull
    private String password;

    private boolean isOauth = false;
}
