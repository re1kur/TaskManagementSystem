package com.example.software.design.security;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.entity.Role;
import com.example.software.design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class CustomJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    UserService userService;

    @Autowired
    public CustomJwtConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        String username = jwt.getClaimAsString("name");
        Collection<Role> authorities;
        Optional<ReadUser> optionalUser = userService.readUserByEmail(email);
        if (optionalUser.isEmpty()) {
            WriteUser writeUser = WriteUser.builder()
                    .email(email)
                    .username(username)
                    .isOauth(true)
                    .build();
            userService.saveUser(writeUser);
        }
        SecurityUser user = (SecurityUser) userService.loadUserByUsername(email);

        authorities = user.getAuthorities();
        return new JwtAuthenticationToken(jwt, authorities, email);
    }
}
