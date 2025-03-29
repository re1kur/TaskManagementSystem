package com.example.software.design.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfiguration {

    @Value("${custom.mailService.queue.verification}")
    private String verificationQueue;

    @Value("${custom.mailService.exchange.verification}")
    private String verificationExchange;

    @Value("${custom.mailService.routingKey.verification}")
    private String verificationRoutingKey;

    @Value("${custom.mailService.queue.notification}")
    private String notificationQueue;

    @Value("${custom.mailService.exchange.notification}")
    private String notificationExchange;

    @Value("${custom.mailService.routingKey.notification}")
    private String notificationRoutingKey;

    @Bean
    public Queue notificationQueue () {
        return new Queue(notificationQueue);
    }

    @Bean
    public Queue verificationQueue () {
        return new Queue(verificationQueue);
    }

    @Bean
    public Exchange notificationExchange() {
        return new TopicExchange(notificationExchange);
    }

    @Bean
    public Exchange verificationExchange() {
        return new TopicExchange(verificationExchange);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, Exchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue).to(notificationExchange)
                .with(notificationRoutingKey).noargs();
    }

    @Bean
    public Binding verificationBinding(Queue verificationQueue, Exchange verificationExchange) {
        return BindingBuilder.bind(verificationQueue).to(verificationExchange)
                .with(verificationRoutingKey).noargs();
    }
}
