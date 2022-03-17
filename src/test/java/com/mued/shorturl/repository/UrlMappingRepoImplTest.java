package com.mued.shorturl.repository;

import com.mued.shorturl.models.UrlMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest(classes = {UrlMappingRepoImplTest.class})
class UrlMappingRepoImplTest {

    private final String _id = "07e54b2d";
    String validUrl;
    String invalidUrl;
    public UrlMapping urlMapping;
    public UrlMappingRepoImpl repo;
    @MockBean
    public RedisTemplate<String, UrlMapping> redisTemplate;
    @Mock
    public ValueOperations<String, UrlMapping> valueOperations;
    @Mock
    public SetOperations<String, UrlMapping> setOperations;

    @BeforeEach
    void setUp() {
        initMocks(this);
        validUrl = "https://localhost.com/spring-boot-url-shortener";
        invalidUrl = "htts://localhost.com/spring-boot-url-shortener";
        repo = new UrlMappingRepoImpl(redisTemplate);
        urlMapping = new UrlMapping(_id, validUrl, null);
    }

    @Test
    public void shouldInsertAndGetFullurl() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Optional<UrlMapping> url = repo.saveShortenUrl(new UrlMapping(_id,
                validUrl,
                LocalDateTime.now()));

        assertThat(url).isNotNull();

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.getOperations()).thenReturn(null);
        Optional<UrlMapping> urlOriginal = repo.getOriginalUrl(validUrl);

        assertThat(urlOriginal).isNotPresent();

        when(valueOperations.get(_id)).thenReturn(urlMapping);
        Optional<UrlMapping> urlOriginal2 = repo.getOriginalUrl(validUrl);

        assertThat(urlOriginal2).isNotNull();
    }
}