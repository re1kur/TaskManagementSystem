package re1kur.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public void register(@RequestBody UserPayload payload) throws UserRegistrationException {
        service.register(payload);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserPayload payload) throws UserLoginException {
        return service.login(payload);
    }
}
