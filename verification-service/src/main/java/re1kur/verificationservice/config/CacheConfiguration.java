package re1kur.verificationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "re1kur.verificationservice.repository")
public class CacheConfiguration {
}
