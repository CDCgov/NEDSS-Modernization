package gov.cdc.nbs.redirect.incoming;

import gov.cdc.nbs.AuthorizedUserResolver;
import gov.cdc.nbs.config.security.SecurityProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A {@code Filter} that ensures a redirected request from Classic NBS has the required authorization cookies and that
 * the user is still authorized.  An unauthorized user will be redirected to `/nbs/timeout`/
 */
class RedirectedAuthenticationFilter implements Filter {

    private static final String USER_COOKIE_NAME = "nbs_user";
    private static final String J_SESSION_COOKIE_NAME = "JSESSIONID";

    private final AuthorizedUserResolver resolver;
    private final SecurityProperties securityProperties;

    RedirectedAuthenticationFilter(
        final AuthorizedUserResolver resolver,
        final SecurityProperties securityProperties
    ) {
        this.resolver = resolver;
        this.securityProperties = securityProperties;
    }

    @Override
    public void doFilter(
        final ServletRequest request,
        final ServletResponse response,
        final FilterChain chain
    )
        throws IOException, ServletException {

        if (
            request instanceof HttpServletRequest incoming
                && response instanceof HttpServletResponse outgoing
        ) {
            String session = resolveSessionIdentifier(incoming.getCookies());

            String user = resolver.resolve(session).orElse(null);

            if (user != null) {
                allowed(outgoing, user, session);
                chain.doFilter(incoming, outgoing);
            } else {
                timeout(outgoing);
            }
        }

    }



    private String resolveSessionIdentifier(final Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (var cookie : cookies) {
            if (cookie.getName().equals(J_SESSION_COOKIE_NAME)) {
                var value = cookie.getValue();
                if (value != null && value.contains(".")) {
                    value = value.substring(0, value.indexOf("."));
                }
                return value;
            }
        }
        return null;
    }

    private void allowed(
        final HttpServletResponse response,
        final String user,
        final String session
    ) {
        response.addCookie(createCookie(USER_COOKIE_NAME, user));
        response.addCookie(createCookie(J_SESSION_COOKIE_NAME, session));
    }

    private Cookie createCookie(final String name, final String value) {
        var cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(securityProperties.getTokenExpirationSeconds());
        return cookie;
    }

    private void timeout(final HttpServletResponse response) {
        response.setStatus(HttpStatus.FOUND.value());
        response.setHeader(HttpHeaders.LOCATION, "/nbs/timeout");
    }

}
