package re1kur.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableDiscoveryClient
public class ApplicationConfiguration {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper serializer() {
        ObjectMapper serializer = new ObjectMapper();
        serializer.findAndRegisterModules();
        return serializer;
    }
}
