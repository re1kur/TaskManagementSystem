package re1kur.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import re1kur.userservice.dto.UserPayload;
import re1kur.userservice.exception.UserLoginException;
import re1kur.userservice.exception.UserRegistrationException;
import re1kur.userservice.service.UserService;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("register")
    public void register(@Validated @RequestBody UserPayload payload) throws UserRegistrationException {
        service.register(payload);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserPayload payload) throws UserLoginException {
        return service.login(payload);
    }

    @GetMapping("checkUser")
    public ResponseEntity<String> checkUser(@RequestParam String email) {
        return service.checkUser(email);
    }
}
