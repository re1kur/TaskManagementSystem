package com.example.software.design.service.impl;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.UserDto;
import com.example.software.design.dto.user.Role;
import com.example.software.design.dto.mail.VerificationCode;
import com.example.software.design.repository.UserClient;
import com.example.software.design.security.SecurityUser;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.service.UserService;
import com.example.software.design.service.VerificationCodeService;
import com.example.software.design.util.exceptions.VerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class DefaultUserService implements UserService {

    private final VerificationCodeService codeService;
    private final UserClient client;

    @Autowired
    public DefaultUserService(
            VerificationCodeService codeService,
            UserClient client
    ) {
        this.codeService = codeService;
        this.client = client;
    }

    @Override
    public Optional<ReadUser> read(Integer id) {
        return client.read(id);
    }

    @Override
    @Transactional
    public void write(WriteUser user) {
        client.save(user);
        codeService.write(user.getEmail());
    }

    @Override
    public List<ReadUser> readAll() {
        return client.readAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDto> mayBeUser = client.loadByEmail(email);
        UserDto user = mayBeUser
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        if (!user.isVerified())
            throw new UsernameNotFoundException("User is not verified. Verify email.");
        return SecurityUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singleton(Role.valueOf(user.getRole())))
                .build();
    }

    @Override
    public Optional<ReadUser> readByEmail(String email) {
        return client.readByEmail(email);
    }

    @Override
    public Map<String, String> getInfo(Authentication authentication) {
        Map<String, String> info = new HashMap<>();
        if (authentication instanceof JwtAuthenticationToken jwtAuthentication) {
            Jwt token = jwtAuthentication.getToken();
            info.put("email", token.getClaimAsString("email"));
            info.put("username", token.getClaimAsString("name"));
            info.put("authorities", jwtAuthentication.getAuthorities().toString());
        } else {
            SecurityUser user = (SecurityUser) authentication.getPrincipal();
            info.put("email", user.getEmail());
            info.put("username", user.getUsername());
            info.put("authorities", user.getAuthorities().toString());
        }
        return info;
    }

    @Override
    @Transactional
    public void verify(String email, String code, boolean sendNew) throws VerificationException, UsernameNotFoundException {
        Optional<UserDto> mayBeUser = client.loadByEmail(email);
        if (mayBeUser.isEmpty()) throw new UsernameNotFoundException("The user not found: " + email);
        UserDto user = mayBeUser.get();
        if (user.isVerified()) throw new VerificationException("The user is already verified. Sign in.");
        if (sendNew) {
            codeService.write(email);
            throw new VerificationException("New code is generated. Check the mail and try again.");
        }
        Optional<VerificationCode> mayBeCode = codeService.read(email);
        if (mayBeCode.isEmpty()) {
            codeService.write(email);
            throw new VerificationException("Code is regenerated. Check the mail and try again.");
        }
        VerificationCode verificationCode = mayBeCode.get();
        if (verificationCode.isExpires()) {
            codeService.write(email);
            throw new VerificationException("Code is expired. Code is regenerated. Check the mail and try again.");
        }
        boolean isEquals = verificationCode.getCode().equals(code);
        if (!isEquals) throw new VerificationException("Code is wrong");
        else {
            user.setVerified(true);
            client.update(user);
            codeService.delete(email);
        }
    }
}
