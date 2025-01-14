package gov.cdc.nbs.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class AddCookie {

    public static void to(final String name, final String value, final HttpHeaders headers) {
        String cookie = ResponseCookie.from(name, value)
            .path("/nbs")
            .sameSite("Strict")
            .httpOnly(true)
            .secure(true)
            .build()
            .toString();
        headers.add(HttpHeaders.SET_COOKIE, cookie);



    }

    private AddCookie() {
    }

}
