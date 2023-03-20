package gov.cdc.nbs.service;

import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.entity.enums.SecurityEventType;
import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.entity.odse.SecurityLog;
import gov.cdc.nbs.repository.AuthUserRepository;
import gov.cdc.nbs.repository.SecurityLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RedirectionService {
    private static final String USER_COOKIE_NAME = "nbs_user";
    private static final String JSESSION_COOKIE_NAME = "JSESSIONID";

    private final SecurityLogRepository securityLogRepository;
    private final AuthUserRepository authUserRepository;
    private final SecurityProperties securityProperties;

    /**
     * Get the user from the session, create userId and token cookies. Create RedirectView from url, or to
     * '/nbs/timeout' if session is invalid
     */
    public RedirectView handleRedirect(String url, HttpServletRequest request, HttpServletResponse response) {
        var jsessionId = getJsessionId(request.getCookies());
        var user = getUserFromSession(jsessionId);
        if (user.isPresent()) {
            var userId = user.get().getUserId();
            response.addCookie(createCookie(USER_COOKIE_NAME, userId));
            response.addCookie(createCookie(JSESSION_COOKIE_NAME, jsessionId));
            return new RedirectView(url);
        } else {
            return new RedirectView("/nbs/timeout");
        }
    }

    private Cookie createCookie(String name, String value) {
        var cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(securityProperties.getTokenExpirationSeconds());
        return cookie;
    }

    private Optional<AuthUser> getUserFromSession(String jsessionId) {
        return Optional.ofNullable(jsessionId)
                .map(securityLogRepository::findBySessionIdOrderByEventTimeDesc)
                .map(this::getUserFromSecurityLogs);
    }

    private AuthUser getUserFromSecurityLogs(List<SecurityLog> logs) {
        // Are there log entries for the session? Has the session been logged out?
        if (logs.isEmpty() ||
                logs.stream().anyMatch(l -> l.getEventTypeCd().equals(SecurityEventType.LOGOUT))) {
            return null;
        } else {
            var entryId = logs.get(0).getNedssEntryId();
            return authUserRepository.findByNedssEntryId(entryId).orElse(null);
        }
    }

    private String getJsessionId(Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (var cookie : cookies) {
            if (cookie.getName().equals(JSESSION_COOKIE_NAME)) {
                var value = cookie.getValue();
                if (value != null && value.indexOf(".") > -1) {
                    value = value.substring(0, value.indexOf("."));
                }
                return value;
            }
        }
        return null;
    }
}
