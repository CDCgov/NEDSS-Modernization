package gov.cdc.nbs.web;

import org.springframework.http.HttpHeaders;

import java.util.function.Consumer;

public class RemoveCookie {

    public static void from(final String name, final HttpHeaders headers) {
        String cookie = name + "=; SameSite=Strict; HttpOnly; Secure; Max-Age=0";
        headers.add(HttpHeaders.SET_COOKIE, cookie);
    }

    public static Consumer<HttpHeaders> removeCookie(final String name) {
        return headers -> from(name, headers);
    }

    private RemoveCookie() {
    }

}
