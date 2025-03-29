package com.example.software.design.controller;

import com.example.software.design.dto.user.ReadUser;
import com.example.software.design.dto.user.WriteUser;
import com.example.software.design.service.UserService;
import com.example.software.design.service.impl.DefaultUserService;
import com.example.software.design.util.exceptions.ValidationException;
import com.example.software.design.util.exceptions.VerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService service;

    @Autowired
    public UserController(DefaultUserService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping("register")
    public void registerUser(@Validated @RequestBody WriteUser writeUser, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) service.saveUser(writeUser);
        else throw new ValidationException(bindingResult.getAllErrors(), "User is not valid");
    }

    @Transactional
    @PostMapping("verify")
    public ResponseEntity<String> verifyUser(@RequestParam("email") String email, @RequestParam("code") String code, @RequestParam(required = false, name = "new") String sendNew) throws VerificationException {
        service.verify(email, code, sendNew != null);
        return ResponseEntity.ok("The user has been verified. Try to sign in.");
    }

    @GetMapping("list")
    public List<ReadUser> readAllUsers() {
        return service.readAllUsers();
    }

    @GetMapping("{id}")
    public ResponseEntity<ReadUser> getUser(@PathVariable int id) {
        Optional<ReadUser> mayBeUser = service.readUser(id);
        return mayBeUser.map(ResponseEntity.ok()::body)
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @GetMapping("info")
    public ResponseEntity<Map<String, String>> thisUser(
            Authentication authentication) {
        Map<String, String> info = service.getInfo(authentication);
        return ResponseEntity.ok().body(info);
    }
}
