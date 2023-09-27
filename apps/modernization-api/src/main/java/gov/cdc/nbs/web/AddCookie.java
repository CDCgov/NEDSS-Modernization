package gov.cdc.nbs.web;

import org.springframework.http.HttpHeaders;

public class AddCookie {

    public static void to(final String name, final String value, final HttpHeaders headers) {
        String cookie = name + "=" + value + "; SameSite=Strict; HttpOnly; Secure; Max-Age=-1";
        headers.add(HttpHeaders.SET_COOKIE, cookie);
    }

    private AddCookie() {
    }

}
