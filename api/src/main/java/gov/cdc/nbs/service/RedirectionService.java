package gov.cdc.nbs.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.SecurityEventType;
import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.entity.odse.SecurityLog;
import gov.cdc.nbs.exception.RedirectionException;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import gov.cdc.nbs.repository.AuthUserRepository;
import gov.cdc.nbs.repository.SecurityLogRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RedirectionService {
    private final DateTimeFormatter formatter;
    private final SecurityLogRepository securityLogRepository;
    private final AuthUserRepository authUserRepository;
    private final SecurityProperties securityProperties;
    private final String USER_COOKIE_NAME = "nbs_user";
    private static final String NBS_LAST_NAME = "patientSearchVO.lastName";
    private static final String NBS_FIRST_NAME = "patientSearchVO.firstName";
    private static final String NBS_DATE_OF_BIRTH = "patientSearchVO.birthTime";
    private static final String NBS_SEX = "patientSearchVO.currentSex";
    private static final String NBS_ID = "patientSearchVO.localID";
    private static final String NBS_EVENT_TYPE = "patientSearchVO.actType";
    private static final String NBS_EVENT_ID = "patientSearchVO.actId";

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

    /**
     * Maps the www-form-urlencoded search parameters to a PatientFilter
     */
    public PatientFilter getPatientFilterFromParams(Map<String, String> map) {
        var filter = new PatientFilter();
        filter.setLastName(map.get(NBS_LAST_NAME));
        filter.setFirstName(map.get(NBS_FIRST_NAME));
        try {
            if (map.get(NBS_DATE_OF_BIRTH) != null) {
                filter.setDateOfBirth(
                        LocalDateTime.parse(map.get(NBS_DATE_OF_BIRTH), formatter).toInstant(ZoneOffset.UTC));
            }
            if (map.get(NBS_SEX) != null) {
                filter.setGender(Gender.valueOf(map.get(NBS_SEX)));
            }
            if (map.get(NBS_ID) != null) {
                filter.setId(Long.parseLong(map.get(NBS_ID)));
            }
            if (map.get(NBS_EVENT_TYPE) != null && map.get(NBS_EVENT_ID) != null) {
                var type = map.get(NBS_EVENT_TYPE);
                if (type.equals("P10006")) {// Vaccine
                    filter.setVaccinationId(map.get(NBS_EVENT_ID));
                } else if (type.equals("P10005")) { // Treatment
                    filter.setTreatmentId(map.get(NBS_EVENT_ID));
                }
            }
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new RedirectionException("Failed to generate patient filter from search params");
        }
        return filter;
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
