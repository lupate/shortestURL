package com.mued.shorturl.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class UrlMapping {

    @Id
    private String id;
    private String url;
    private LocalDateTime creationDate;

    public UrlMapping() {
    }

    public UrlMapping(String url) {
        this.url = url;
    }

    public UrlMapping(String id, String url, LocalDateTime creationDate) {
        this.id = id;
        this.url = url;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UrlMapping{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
