package com.mued.shorturl.service;

import com.mued.shorturl.models.UrlMapping;

import java.util.Optional;

public interface UrlMappingService {
    Optional<UrlMapping> getOriginalUrl(String shortUrl);

    Optional<UrlMapping> generateShortenlUrl(UrlMapping urlMapping);
}
