package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.config.SecurityProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public record NBSToken(String value) {

    private static final String NBS_TOKEN_NAME = "nbs_token";

    public void apply(
        final SecurityProperties properties,
        final HttpServletResponse response
    ) {
        Cookie cookie = asCookie();
        cookie.setMaxAge(properties.getTokenExpirationSeconds());

        response.addCookie(cookie);

    }

    @SuppressWarnings({"squid:S2092"})
    public Cookie asCookie() {
        Cookie cookie = new Cookie(NBS_TOKEN_NAME, value());
        cookie.setPath("/");
        return cookie;
    }

}
