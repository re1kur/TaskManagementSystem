package com.example.software.design.service;

import com.example.software.design.dto.ReadUser;
import com.example.software.design.entity.User;
import com.example.software.design.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(UserRepository userRepository) {
        logger.info("bean UserService is created.");
        this.userRepository = userRepository;
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public ReadUser map(User user) {
        return ReadUser.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
    }
}
