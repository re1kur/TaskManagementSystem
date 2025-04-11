package re1kur.userservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MQConfiguration {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queues.welcome-queue.name}")
    private String welcomeQueue;

    @Value("${rabbitmq.queues.verification-registration-queue.name}")
    private String verificationRegistrationQueue;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue welcomeQueue() {
        return new Queue(welcomeQueue);
    }

    @Bean
    public Queue verificationRegistrationQueue() {
        return new Queue(verificationRegistrationQueue);
    }

    @Bean
    public Binding welcomeQueueBinding() {
        return BindingBuilder
                .bind(welcomeQueue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding verificationRegistrationQueueBinding() {
        return BindingBuilder
                .bind(verificationRegistrationQueue())
                .to(exchange())
                .with(routingKey);
    }


}
