package re1kur.userservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re1kur.userservice.dto.UserPayload;
import re1kur.userservice.entity.User;
import re1kur.userservice.exception.UserRegistrationException;
import re1kur.userservice.mq.message.UserCheckResponseMessage;
import re1kur.userservice.mq.message.UserRegistrationMessage;
import re1kur.userservice.exception.UserLoginException;
import re1kur.userservice.jwt.JwtProvider;
import re1kur.userservice.mapper.UserMapper;
import re1kur.userservice.mq.sender.MessageSender;
import re1kur.userservice.repository.UserRepository;
import re1kur.userservice.service.UserService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder encoder;
    private final MessageSender publisher;
    private final ObjectMapper serializer;

    @Override
    @Transactional
    public void register(UserPayload payload) throws UserRegistrationException {
        if (repo.existsByEmail(payload.email()))
            throw new UserRegistrationException("User with this email is already exists");
        User user = mapper.write(payload);
        user = repo.save(user);
        publisher.sendUserRegistrationMessage(payload.email());
    }

    @Override
    @Transactional
    public ResponseEntity<String> login(UserPayload payload) throws UserLoginException {
        User user = repo.findByEmail(payload.email())
                .orElseThrow(() -> new UserLoginException("Credentials are invalid."));
        if (!encoder.matches(payload.password(), user.getPassword()))
            throw new UserLoginException("Credentials are invalid.");
        if (!user.getVerified()) throw new UserLoginException("User is not verified.");

        return ResponseEntity.status(HttpStatus.FOUND).body(jwtProvider.provide(user));
    }

    @Override
    @SneakyThrows
    public ResponseEntity<String> checkUser(String email) {
        Optional<User> mayBeUser = repo.findByEmail(email);
        boolean isExists = mayBeUser.isPresent();
        boolean isVerified = false;
        if (isExists) {
            isVerified = mayBeUser.get().getVerified();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(serializer.writeValueAsString(
                        new UserCheckResponseMessage(email, isExists, isVerified)));
    }

}
