package cepredis.config;

import cepredis.models.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Address> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Address> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // Configurações adicionais, se necessário
        return template;
    }
}
