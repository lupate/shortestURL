package com.mued.shorturl.common;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlUtil {
    private UrlUtil() {
    }

    public static boolean validateURL(String url) {
        UrlValidator validator = new UrlValidator(new String[]{"https", "http"});
        return validator.isValid(url);
    }
}
