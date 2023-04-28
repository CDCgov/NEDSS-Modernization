package gov.cdc.nbs.authorization;

import gov.cdc.nbs.config.security.SecurityProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public record NBSUserCookie(String user) {

    private static final String USER_COOKIE_NAME = "nbs_user";

    public void apply(
        final SecurityProperties properties,
        final HttpServletResponse response
    ) {
        Cookie cookie = new Cookie(USER_COOKIE_NAME, user);
        cookie.setPath("/");
        cookie.setMaxAge(properties.getTokenExpirationSeconds());

        response.addCookie(cookie);

    }

}
