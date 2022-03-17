package com.mued.shorturl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mued.shorturl.models.UrlMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * By default the auto configured RedisTemplate provided by Sprint Boot
 * works well with a String based Key/Value pair.
 * However, we will store Key as a String and Value as a JSON object.
 * {
 *     "id": "123456",
 *     "url": "https://example.com/spring-boot-url-short",
 *     "created": "2022-02-11T13:30:15.449"
 * }
 */
@Configuration
public class RedisConfig {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    RedisConnectionFactory connectionFactory;

    @Bean(name = { "redisTemplate"})
    RedisTemplate<String, UrlMapping> redisTemplate(){
        final RedisTemplate<String, UrlMapping> redisTemplate
                = new RedisTemplate<>();
        Jackson2JsonRedisSerializer valueSerializer =
                new Jackson2JsonRedisSerializer(UrlMapping.class);

        valueSerializer.setObjectMapper(mapper);
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(valueSerializer);
        return redisTemplate;
    }

}
