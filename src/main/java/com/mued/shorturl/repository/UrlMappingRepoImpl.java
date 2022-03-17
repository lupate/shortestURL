package com.mued.shorturl.repository;

import com.mued.shorturl.models.UrlMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class UrlMappingRepoImpl implements UrlMappingRepo {

    @Value("${redis.ttl}")
    private long ttl;

//    @Value("${domain.name}")
//    private String domainName;

    private RedisTemplate<String, UrlMapping> redisTemplate;

    public UrlMappingRepoImpl(RedisTemplate<String, UrlMapping> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<UrlMapping> getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(shortUrl));
    }

    @Override
    public Optional<UrlMapping> saveShortenUrl(UrlMapping url) {
        redisTemplate.opsForValue().set(url.getId(), url, ttl, TimeUnit.SECONDS);
        return Optional.of(url);
    }
}
