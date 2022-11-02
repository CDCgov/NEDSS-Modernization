package gov.cdc.nbs.service;

import static java.util.Map.entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.RedirectView;

import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.entity.enums.SecurityEventType;
import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.entity.odse.SecurityLog;
import gov.cdc.nbs.repository.AuthUserRepository;
import gov.cdc.nbs.repository.SecurityLogRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RedirectionService {
    private final SecurityLogRepository securityLogRepository;
    private final AuthUserRepository authUserRepository;
    private final SecurityProperties securityProperties;
    private final String USER_COOKIE_NAME = "nbs_user";

    /**
     * Get the user from the session, create userId and token cookies. Create
     * RedirectView from url, or to '/nbs/timeout' if session is invalid
     */
    public RedirectView handleRedirect(String url, HttpServletRequest request, HttpServletResponse response) {
        var user = getUserFromSession(request.getCookies());
        if (user.isPresent()) {
            var userId = user.get().getUserId();
            response.addCookie(createUserIdCookie(userId));
            return new RedirectView(url);
        } else {
            return new RedirectView("/nbs/timeout");
        }
    }

    private Cookie createUserIdCookie(String userId) {
        var cookie = new Cookie(USER_COOKIE_NAME, userId);
        cookie.setPath("/");
        cookie.setMaxAge(securityProperties.getTokenExpirationSeconds());
        return cookie;
    }

    private static final Map<String, String> fieldMappings = Map.ofEntries(
            entry("patientSearchVO.lastName", "lastName"),
            entry("patientSearchVO.firstName", "firstName"),
            entry("patientSearchVO.birthTime", "DateOfBirth"),
            entry("patientSearchVO.currentSex", "sex"),
            entry("patientSearchVO.actType", "eventType"),
            entry("patientSearchVO.actId", "eventId"),
            entry("patientSearchVO.localID", "id")

    );

    /**
     * Maps the www-form-urlencoded search parameters to our search params
     */
    public Map<String, String> getSearchAttributes(Map<String, String> map) {
        var searchFields = new HashMap<String, String>();
        fieldMappings.forEach((NBSKey, Key) -> {
            if (StringUtils.hasText(map.get(NBSKey))) {
                searchFields.put(Key, map.get(NBSKey));
            }
        });
        return searchFields;
    }

    public List<SecurityLog> getSecurityLogsForSessionId(String sessionId) {
        return securityLogRepository.findBySessionIdOrderByEventTimeDesc(sessionId);
    }

    public Optional<AuthUser> getUserFromSession(Cookie[] cookies) {
        return Optional.ofNullable(getJsessionId(cookies))
                .map(this::getSecurityLogsForSessionId)
                .map(this::getUserFromSecurityLogs);
    }

    private AuthUser getUserFromSecurityLogs(List<SecurityLog> logs) {
        if (logs.isEmpty() ||
                logs.stream().filter(l -> l.getEventTypeCd().equals(SecurityEventType.LOGOUT))
                        .findFirst()
                        .isPresent()) {
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
            if (cookie.getName().equals("JSESSIONID")) {
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
