package gov.cdc.nbs.authorization;

import gov.cdc.nbs.config.security.SecurityProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public record NBSUserCookie(String user) {

    private static final String USER_COOKIE_NAME = "nbs_user";

    @SuppressWarnings({"squid:S2092", "squid:S3330"})
    public void apply(
        final SecurityProperties properties,
        final HttpServletResponse response
    ) {
        Cookie cookie = new Cookie(USER_COOKIE_NAME, user);
        cookie.setPath("/");
        cookie.setMaxAge(properties.getTokenExpirationSeconds());
        // S2092 intra-container communication is currently not secure
        cookie.setSecure(true);
        // S3330 currently read by frontend codebase
        cookie.setHttpOnly(false);

        response.addCookie(cookie);

    }

}
