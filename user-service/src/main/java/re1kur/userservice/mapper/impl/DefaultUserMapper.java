package re1kur.userservice.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import re1kur.userservice.dto.UserPayload;
import re1kur.userservice.entity.User;
import re1kur.userservice.mapper.UserMapper;

@Component
@RequiredArgsConstructor
public class DefaultUserMapper implements UserMapper {
    private final BCryptPasswordEncoder encoder;

    @Override
    public User write(UserPayload payload) {
        User build = User.builder()
                .email(payload.email())
                .password(encoder.encode(payload.password()))
                .build();
        if (payload.oauth() != null) {
            build.setOauth(payload.oauth());
        }
        return build;
    }
}
