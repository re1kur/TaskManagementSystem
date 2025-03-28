package com.example.software.design.service.impl;

import com.example.software.design.dto.mail.EmailRequest;
import com.example.software.design.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DefaultMailService implements MailService {
    private static final Logger log = LoggerFactory.getLogger(DefaultMailService.class);
    private final WebClient mailClient;

    public DefaultMailService(WebClient mailClient) {
        this.mailClient = mailClient;
    }

    @Override
    public void sendVerifyCodeMail(String email, String code) {
        log.info("Sending verification code '{}' mail to {}.", code, email);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(email)
                .subject("Verify Code")
                .body("Verify Code is " + code)
                .build();
        try {
            Mono<String> response = mailClient.post()
                    .uri("/send")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(emailRequest))
                    .retrieve()
                    .bodyToMono(String.class);

            response.subscribe(
                    result -> log.info("Email sent successfully: {}", result),
                    error -> log.error("Error sending email: ", error)
            );
        } catch (Exception e) {
            log.error("Error sending email: ", e);
        }
    }
}
