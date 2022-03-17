package com.mued.shorturl.models;

public class Error {

    private String field;
    private String value;
    private String message;

    public Error(String field, String value, String message) {
        this.field = field;
        this.value = value;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
