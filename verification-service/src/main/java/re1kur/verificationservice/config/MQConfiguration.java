package re1kur.verificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfiguration {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queues.notificationQueue.name}")
    private String notificationQueue;

    @Value("${rabbitmq.queues.notificationQueue.rout-key}")
    private String notificationQueueRoutKey;

    @Value("${rabbitmq.queues.verificationQueue.name}")
    private String verificationQueue;

    @Value("${rabbitmq.queues.verificationQueue.rout-key}")
    private String verificationQueueRoutKey;


    @Bean
    public Queue notificationUserQueue() {
        return new Queue(notificationQueue);
    }

    @Bean
    public Queue verificationUserQueue() {
        return new Queue(verificationQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding notificationUserBinding() {
        return BindingBuilder
                .bind(notificationUserQueue())
                .to(exchange())
                .with(notificationQueueRoutKey);
    }

    @Bean
    public Binding verificationUserBinding() {
        return BindingBuilder
                .bind(verificationUserQueue())
                .to(exchange())
                .with(verificationQueueRoutKey);
    }
}
