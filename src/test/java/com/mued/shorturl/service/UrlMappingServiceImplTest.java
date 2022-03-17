package com.mued.shorturl.service;

import com.mued.shorturl.models.UrlMapping;
import com.mued.shorturl.repository.UrlMappingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UrlMappingServiceImplTest.class})
class UrlMappingServiceImplTest {

    @Mock
    UrlMappingRepo urlMappingRepo;

    @InjectMocks
    UrlMappingServiceImpl urlMappingServiceImpl;

    UrlMapping urlMapping;
    String shortUrl;
    String longUrl;
    String invalidUrl;

    @BeforeEach
    void setUp() {
        shortUrl = "07e54b2d";
        longUrl = "https://localhost.com/spring-boot-url-shortener";
        invalidUrl = "htts://localhost.com/spring-boot-url-shortener";
        urlMapping = new UrlMapping("07e54b2d",
                "https://localhost.com/spring-boot-url-shortener", null);
    }

    @Test
    @Order(1)
    @DisplayName(value = "should return original URL when getting a valid shorten URL")
    void getOriginalUrlSuccess() {
        when(urlMappingRepo.getOriginalUrl(shortUrl))
                .thenReturn(Optional.ofNullable(urlMapping));

        assertTrue(urlMappingServiceImpl.getOriginalUrl(shortUrl).isPresent());
        assertTrue(urlMappingServiceImpl.getOriginalUrl(shortUrl).get().getUrl().startsWith("http"));
    }

    @Test
    @Order(2)
    @DisplayName(value = "should not return original URL when getting a invalid shorten URL")
    void getOriginalUrlFail() {
        when(urlMappingRepo.getOriginalUrl(shortUrl))
                .thenReturn(Optional.ofNullable(null));

        assertFalse(urlMappingServiceImpl.getOriginalUrl(invalidUrl).isPresent());
    }

    @Test
    @Order(3)
    @DisplayName(value = "should return a short URL when getting a valid URL")
    void generateShortenlUrlSuccess() {
        when(urlMappingRepo.saveShortenUrl(urlMapping))
                .thenReturn(Optional.ofNullable(urlMapping));

        assertTrue(urlMappingServiceImpl.generateShortenlUrl(urlMapping).isPresent());
        assertTrue(urlMappingServiceImpl.generateShortenlUrl(urlMapping).get().getId().length() == 8);
    }

    @Test
    @Order(4)
    @DisplayName(value = "should not return a short URL when getting a invalid URL")
    void generateShortenlUrlFail() {
        urlMapping.setUrl(invalidUrl);
        when(urlMappingRepo.saveShortenUrl(urlMapping))
                .thenReturn(Optional.ofNullable(urlMapping));

        assertFalse(urlMappingServiceImpl.generateShortenlUrl(urlMapping).isPresent());
        assertTrue(urlMappingServiceImpl.generateShortenlUrl(urlMapping).isEmpty());
    }
}