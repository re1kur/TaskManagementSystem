package com.example.software.design.service;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.security.SecurityUser;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.entity.User;
import com.example.software.design.mapper.UserMapper;
import com.example.software.design.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository repo;
    private final UserMapper mapper;

    @Autowired
    public UserService(
            UserRepository repo,
            UserMapper mapper
    ) {
        logger.info("bean UserService is created.");
        this.repo = repo;
        this.mapper = mapper;
    }

    public Optional<ReadUser> readUser(Integer id) {
        return repo.findById(id).map(mapper::mapRead);
    }

    @Transactional
    public void saveUser(WriteUser writeUser) {
        User user = mapper.mapEntity(writeUser);
        repo.save(user);
    }

    public List<ReadUser> readAllUsers() {
        return repo.findAll().stream().map(mapper::mapRead).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email).map(user -> SecurityUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singleton(user.getRole()))
                .build()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public Optional<ReadUser> readUserByEmail(String email) {
        return repo.findByEmail(email).map(mapper::mapRead);
    }

    public void updateUser(WriteUser writeUser) {
        mapper.mapEntity(writeUser);
    }

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
}
