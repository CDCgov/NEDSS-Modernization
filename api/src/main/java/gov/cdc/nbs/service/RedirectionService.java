package gov.cdc.nbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;

import static java.util.Map.entry;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
