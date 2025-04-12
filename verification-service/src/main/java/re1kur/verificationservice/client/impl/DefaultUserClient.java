package re1kur.verificationservice.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import re1kur.verificationservice.client.UserClient;
import re1kur.verificationservice.mq.message.UserCheckResponseMessage;

@Component
@RequiredArgsConstructor
public class DefaultUserClient implements UserClient {
    private final WebClient client;

    @Override
    public UserCheckResponseMessage checkUser(String email) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/checkUser")
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .bodyToMono(UserCheckResponseMessage.class)
                .block();
    }
}
