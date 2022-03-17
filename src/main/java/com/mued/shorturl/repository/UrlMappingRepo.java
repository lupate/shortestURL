package com.mued.shorturl.repository;

import com.mued.shorturl.models.UrlMapping;

import java.util.Optional;

public interface UrlMappingRepo {
    Optional<UrlMapping> getOriginalUrl(String shortUrl);

    Optional<UrlMapping> saveShortenUrl(UrlMapping url);
}
