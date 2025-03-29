package com.example.software.design.service.impl;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.entity.redis.VerificationCode;
import com.example.software.design.security.SecurityUser;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.entity.jpa.User;
import com.example.software.design.mapper.UserMapper;
import com.example.software.design.repository.postgres.UserRepository;
import com.example.software.design.service.UserService;
import com.example.software.design.service.VerificationCodeService;
import com.example.software.design.util.exceptions.VerificationException;
import org.slf4j.LoggerFactory;
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

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserRepository repo;
    private final UserMapper mapper;
    private final VerificationCodeService codeService;

    @Autowired
    public DefaultUserService(
            UserRepository repo,
            UserMapper mapper,
            VerificationCodeService codeService
    ) {
        this.repo = repo;
        this.mapper = mapper;
        this.codeService = codeService;
    }

    @Override
    public Optional<ReadUser> readUser(Integer id) {
        return repo.findById(id).map(mapper::mapRead);
    }

    @Transactional
    @Override
    public void saveUser(WriteUser writeUser) {
        User user = mapper.mapEntity(writeUser);
        repo.save(user);
        codeService.writeCode(user.getEmail());
    }

    @Override
    public List<ReadUser> readAllUsers() {
        return repo.findAll().stream().map(mapper::mapRead).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        if (!user.isVerified())
            throw new UsernameNotFoundException("User is not verified. Verify email.");
        return SecurityUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singleton(user.getRole()))
                .build();
    }

    @Override
    public Optional<ReadUser> readUserByEmail(String email) {
        return repo.findByEmail(email).map(mapper::mapRead);
    }

    @Override
    public void updateUser(WriteUser writeUser) {
        mapper.mapEntity(writeUser);
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
    public void verify(String email, String code, boolean sendNew) throws VerificationException, UsernameNotFoundException {
        Optional<User> mayBeUser = repo.findByEmail(email);
        if (mayBeUser.isEmpty()) throw new UsernameNotFoundException("The user not found: " + email);
        User user = mayBeUser.get();
        if (user.isVerified()) throw new VerificationException("The user is already verified. Sign in.");
        if (sendNew) {
            codeService.regenerateCode(email);
            return;
        }
        Optional<VerificationCode> mayBeCode = codeService.readCode(email);
        if (mayBeCode.isEmpty()) {
            codeService.regenerateCode(email);
            throw new VerificationException("Code is regenerated. Check the mail and try again.");
        }
        VerificationCode verificationCode = mayBeCode.get();
        if (verificationCode.isExpires()) {
            codeService.regenerateCode(email);
            throw new VerificationException("Code is expired. Code is regenerated. Check the mail and try again.");
        }
        boolean isEquals = verificationCode.getCode().equals(code);
        if (!isEquals) throw new VerificationException("Code is wrong");
        else {
            user.setVerified(true);
            repo.save(user);
            codeService.deleteCode(email);
        }
    }
}
