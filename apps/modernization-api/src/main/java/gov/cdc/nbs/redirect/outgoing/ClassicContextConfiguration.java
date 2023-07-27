package gov.cdc.nbs.redirect.outgoing;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Configuration
class ClassicContextConfiguration {

    private static final String SESSION_COOKIE_NAME = "JSESSIONID";
    private static final String NBS_USER_COOKIE_NAME = "nbs_user";

    @Bean
    @RequestScope
    ClassicContext classicContext(final HttpServletRequest request) {
        String host = request.getRemoteHost();
        String session = resolveSessionIdentifier(request.getCookies());
        String user = resolveUser(request.getCookies());
        return new DefaultClassicContext(host, user, session);
    }

    private String resolveSessionIdentifier(final Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (var cookie : cookies) {
            if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String resolveUser(final Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (var cookie : cookies) {
            if (cookie.getName().equals(NBS_USER_COOKIE_NAME)) {
                return cookie.getValue();
            }
        }
        return null;
    }
    
}
