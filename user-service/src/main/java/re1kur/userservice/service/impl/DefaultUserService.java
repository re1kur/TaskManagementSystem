package re1kur.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re1kur.userservice.dto.UserPayload;
import re1kur.userservice.entity.User;
import re1kur.userservice.exception.UserLoginException;
import re1kur.userservice.exception.UserRegistrationException;
import re1kur.userservice.jwt.JwtProvider;
import re1kur.userservice.mapper.UserMapper;
import re1kur.userservice.repository.UserRepository;
import re1kur.userservice.service.UserService;


@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void register(UserPayload payload) throws UserRegistrationException {
        User user = mapper.write(payload);
        repo.save(user);
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

}
