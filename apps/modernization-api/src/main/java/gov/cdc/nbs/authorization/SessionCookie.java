package gov.cdc.nbs.authorization;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import gov.cdc.nbs.authentication.config.SecurityProperties;
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
            final HttpServletResponse response) {
        Cookie cookie = asCookie();
        cookie.setMaxAge(properties.getTokenExpirationSeconds());

        response.addCookie(cookie);

    }

    @SuppressWarnings({"squid:S2092"})
    public Cookie asCookie() {
        Cookie cookie = new Cookie(J_SESSION_COOKIE_NAME, identifier);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        // S2092 intra-container communication is currently not secure
        cookie.setSecure(false);
        return cookie;
    }

}
