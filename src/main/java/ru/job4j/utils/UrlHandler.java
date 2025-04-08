package ru.job4j.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlHandler {

    public static final String URL_PATTERN = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$";
    public static final String DOMAIN_PATTERN = "(?:www\\.)?((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}";

    public static String extractLastSegment(String uriString) throws URISyntaxException {
        URI uri = new URI(uriString);
        String path = uri.getPath();
        String[] segments = path.split("/");
        return segments.length > 0 ? segments[segments.length - 1] : "";
    }
}
