package re1kur.verificationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableDiscoveryClient
public class DefaultConfiguration {
    @Value("${services.user-service.url}")
    private String userServiceUrl;

    @LoadBalanced
    @Bean
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder loadBalancedWebClientBuilder) {
        return loadBalancedWebClientBuilder.baseUrl(userServiceUrl).build();
    }
}
