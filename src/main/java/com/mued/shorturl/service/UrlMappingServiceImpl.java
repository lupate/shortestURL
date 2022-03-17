package com.mued.shorturl.service;

import com.google.common.hash.Hashing;
import com.mued.shorturl.common.UrlUtil;
import com.mued.shorturl.models.UrlMapping;
import com.mued.shorturl.repository.UrlMappingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UrlMappingServiceImpl implements UrlMappingService {

    @Autowired
    private UrlMappingRepo urlMappingRepo;

    @Override
    public Optional<UrlMapping> getOriginalUrl(String shortUrl) {
        // return urlMappingRepo.getOriginalUrl(shortUrl)
        // .orElseThrow(() -> new NoSuchElementException("URL does not exist  + shortUrl"));
        return urlMappingRepo.getOriginalUrl(shortUrl);
    }

    @Override
    public Optional<UrlMapping> generateShortenlUrl(UrlMapping urlMapping) {
        if (!UrlUtil.validateURL(urlMapping.getUrl())) {
            return Optional.empty(); // or throw BadRequest Exception
        }
        return urlMappingRepo.saveShortenUrl(generateFullShortedURLObj(urlMapping));
    }

    //Business
    private UrlMapping generateFullShortedURLObj(UrlMapping urlMapping) {
        String shortUrl = Hashing.murmur3_32().hashString(urlMapping.getUrl(), Charset.defaultCharset()).toString();
        urlMapping.setId(shortUrl);
        urlMapping.setCreationDate(LocalDateTime.now());
        return urlMapping;
    }
}
