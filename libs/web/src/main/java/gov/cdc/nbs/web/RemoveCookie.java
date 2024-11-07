package gov.cdc.nbs.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.util.function.Consumer;

public class RemoveCookie {

    public static void from(final String name, final HttpHeaders headers) {
        String cookie = ResponseCookie.from(name,"")
            .path("/nbs")
            .sameSite("Strict")
            .httpOnly(true)
            .secure(true)
            .maxAge(0)
            .build()
            .toString();
        headers.add(HttpHeaders.SET_COOKIE, cookie);
    }

    public static Consumer<HttpHeaders> removeCookie(final String name) {
        return headers -> from(name, headers);
    }

    private RemoveCookie() {
    }

}
