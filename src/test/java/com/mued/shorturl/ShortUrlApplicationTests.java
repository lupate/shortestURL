package com.mued.shorturl;

import com.mued.shorturl.controllers.UrlShortenerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShortUrlApplicationTests {

    @Autowired
    public UrlShortenerController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
