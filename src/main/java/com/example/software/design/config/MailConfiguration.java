package com.example.software.design.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MailConfiguration {

    @Value("${custom.mailService.url}")
    private String mailServiceUrl;

    @Value("${custom.mailService.queues[0].notification}")
    private String notification;

    @Value("${custom.mailService.queues[1].verification}")
    private String verification;

    @Bean
    WebClient mailClient() {
        return WebClient.builder()
                .baseUrl(mailServiceUrl)
                .build();
    }

    @Bean
    Queue notificationQueue () {
        return new Queue(notification);
    }

    @Bean
    Queue verificationQueue () {
        return new Queue(verification);
    }

    @Bean
    Exchange notificationExchange() {
        return new TopicExchange(notification);
    }

    @Bean
    Exchange verificationExchange() {
        return new TopicExchange(verification);
    }

    @Bean
    Binding notificationBinding(Queue notificationQueue, Exchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue).to(notificationExchange)
                .with(notification).noargs();
    }

    @Bean
    Binding verificationBinding(Queue verificationQueue, Exchange verificationExchange) {
        return BindingBuilder.bind(verificationQueue).to(verificationExchange)
                .with(verification).noargs();
    }
}
