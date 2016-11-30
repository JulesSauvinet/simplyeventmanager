package fr.univlyon1.sem.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtils {

    /**
     * Encode une URL en UTF-8
     *
     * @param url
     * @return
     */
    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        return url;
    }
}
