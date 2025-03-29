package com.example.software.design.security;

import com.example.software.design.entity.jpa.Role;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
public class SecurityUser implements UserDetails{
    private final int id;
    private final String username;
    private final String email;
    private final String password;
    private final Collection<Role> authorities;
}
