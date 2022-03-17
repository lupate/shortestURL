package com.mued.shorturl.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlUtilTest {
    String validUrl;
    String invalidUrl;

    @BeforeEach
    void setUp() {
        validUrl = "https://localhost.com/spring-boot-url-shortener";
        invalidUrl = "htts://localhost.com/spring-boot-url-shortener";
    }

    @Test
    void validateURL() {
        assertFalse(UrlUtil.validateURL(invalidUrl));
        assertTrue(UrlUtil.validateURL(validUrl));
    }
}