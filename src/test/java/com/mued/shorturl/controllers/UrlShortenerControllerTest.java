package com.mued.shorturl.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mued.shorturl.models.UrlMapping;
import com.mued.shorturl.service.UrlMappingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UrlShortenerController.class)
@DisplayName(value = "Testing URL shorten APP ..!")
class UrlShortenerControllerTest {

    @MockBean
    private UrlMappingService urlMappingService;

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Nested
    @DisplayName(value = "in case of requesting an original url -> read")
    class ReadingRequests {

        @Test
        @DisplayName(value = "return error when passing invalid shorted url - NOT FOUND")
        void shouldReturnErrorWhenSendInvalidOriginalURL() throws Exception {
            UrlMapping testingUrlMapping = new UrlMapping("07e54b2dqwe", "", null);

            when(urlMappingService.getOriginalUrl(ArgumentMatchers.any()))
                    .thenReturn(Optional.empty());

            mockMvc.perform(get("/api/v1/original/{shortUrl}", testingUrlMapping.getId())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.url").doesNotExist());
        }

        @Test
        @DisplayName(value = "return ok when passing valid shorted url")
        void shouldReturnErrorWhenSendInvalidURL() throws Exception {
            UrlMapping testingUrlMapping = new UrlMapping("07e54b2ds",
                    "https://www.localhost.com/spring-boot-url-shortener", null);

            when(urlMappingService.getOriginalUrl(ArgumentMatchers.any()))
                    .thenReturn(Optional.of(testingUrlMapping));

            mockMvc.perform(get("/api/v1/original/{shortUrl}", testingUrlMapping.getId())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.url").exists())
                    .andDo(print());
        }
    }


    @Nested
    @DisplayName(value = "in case of shorten a long url -> create")
    class CreatingRequests {
        @Test
        @DisplayName(value = "return error when passing empty optional long url")
        void shouldReturnErrorWhenGetEmptyOptional() throws Exception {
            when(urlMappingService.generateShortenlUrl(ArgumentMatchers.any())).thenReturn(Optional.empty());

            String longUrl = "https://localhost.com/spring-boot-url-shortener";
            UrlMapping testingUrlMapping = new UrlMapping(longUrl);

            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/shorten")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(testingUrlMapping));

            MockHttpServletResponse res = mockMvc
                    .perform(requestBuilder)
                    .andReturn()
                    .getResponse();
            assertEquals(400, res.getStatus());
        }

        @Test
        @DisplayName(value = "return error when passing invalid long URL")
        void shouldReturnErrorWhenPassingInvalidURL() throws Exception {
            //when(urlMappingService.generateShortenlUrl(ArgumentMatchers.any())).thenReturn(Optional.empty());

            String longUrl = "htts://localhost.com/spring-boot-url-shortener";
            UrlMapping testingUrlMapping = new UrlMapping(longUrl);

            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/shorten")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(testingUrlMapping));

            int statusCode = mockMvc
                    .perform(requestBuilder)
                    .andReturn()
                    .getResponse()
                    .getStatus();
            assertEquals(400, statusCode);
        }

        @Test
        @DisplayName(value = "return http ok when passing valid long url")
        void shouldReturnOkWhenGetValidUrl() throws Exception {

            String longUrl = "https://localhost.com/spring-boot-url-shortener";
            UrlMapping testingUrlMapping = new UrlMapping(longUrl);

            when(urlMappingService.generateShortenlUrl(ArgumentMatchers.any()))
                    .thenReturn(Optional.of(testingUrlMapping));

            mockMvc.perform(post("/api/v1/shorten")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(testingUrlMapping)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName(value = "return short url when passing valid long url")
        void shouldReturnShortUrlWhenGetValidUrl() throws Exception {

            String longUrl = "https://localhost.com/spring-boot-url-shortener";
            UrlMapping testingUrlMapping = new UrlMapping("07e54b2d", longUrl, null);

            when(urlMappingService.generateShortenlUrl(ArgumentMatchers.any()))
                    .thenReturn(Optional.of(testingUrlMapping));

            mockMvc.perform(post("/api/v1/shorten")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(testingUrlMapping)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.id").isString())
                    .andExpect(jsonPath("$.id").value("07e54b2d"))
                    .andExpect(jsonPath("$.url").value(longUrl))
                    .andDo(print());
        }

    }
}





















