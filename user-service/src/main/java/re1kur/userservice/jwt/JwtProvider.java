package re1kur.userservice.jwt;

import re1kur.userservice.entity.User;

@FunctionalInterface
public interface JwtProvider {
    String provide(User user);
}
