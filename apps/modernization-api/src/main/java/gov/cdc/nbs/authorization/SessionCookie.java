package gov.cdc.nbs.authorization;

import gov.cdc.nbs.config.security.SecurityProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public record SessionCookie(String identifier) {

    private static final String J_SESSION_COOKIE_NAME = "JSESSIONID";

    public static Optional<SessionCookie> resolve(final Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(J_SESSION_COOKIE_NAME)) {
                String identifier = cookie.getValue();

                if (identifier != null && identifier.contains(".")) {
                    identifier = identifier.substring(0, identifier.indexOf("."));
                }

                return Optional.of(new SessionCookie(identifier));
            }
        }
        return Optional.empty();
    }

    public void apply(
        final SecurityProperties properties,
        final HttpServletResponse response
    ) {
        Cookie cookie = asCookie();
        cookie.setMaxAge(properties.getTokenExpirationSeconds());

        response.addCookie(cookie);

    }

    public Cookie asCookie() {
        Cookie cookie = new Cookie(J_SESSION_COOKIE_NAME, identifier);
        cookie.setPath("/");
        return cookie;
    }

}
