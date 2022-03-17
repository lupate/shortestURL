package com.mued.shorturl.controllers;


import com.mued.shorturl.models.Error;
import com.mued.shorturl.models.UrlMapping;
import com.mued.shorturl.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

// we can include spring-data-rest instead of using controllers and comply to naming convension

@RestController
@RequestMapping(value = "/api/v1")
public class UrlShortenerController {

    @Autowired
    private UrlMappingService urlMappingService;

    /**
     *
     * @param shortUrl
     * @return Returns the original URL.
     */
    @GetMapping(path = "/original/{shortUrl}")
    @ResponseBody
    public ResponseEntity getOriginalUrl(@PathVariable String shortUrl) {
        Optional<UrlMapping> url = urlMappingService.getOriginalUrl(shortUrl);

        if (!url.isPresent()) {
            Error error = new Error("id", shortUrl, "This is not a valid URL!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(url);
    }

    /**
     *
     * @param urlMapping
     * @return Returns a shorted URL.
     */
    @PostMapping(path = "/shorten", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity shortenUrl(@RequestBody @NotNull UrlMapping urlMapping) {

        Optional<UrlMapping> url = urlMappingService.generateShortenlUrl(urlMapping);
        if (url.isEmpty()) {
            Error error = new Error("url", urlMapping.getUrl(), "Invalid URL");
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(url);
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this controller
     *
     * @param ex exception
     * @return Error message String
     */
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoSuchElementException.class)
//    public String trurn400(NoSuchElementException ex) {
//        return ex.getMessage();
//    }
}
