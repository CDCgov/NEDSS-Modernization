package gov.cdc.nbs;

import gov.cdc.nbs.entity.enums.SecurityEventType;
import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.entity.odse.SecurityLog;
import gov.cdc.nbs.repository.AuthUserRepository;
import gov.cdc.nbs.repository.SecurityLogRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorizedUserResolver {

    private final SecurityLogRepository securityLogRepository;
    private final AuthUserRepository authUserRepository;

    AuthorizedUserResolver(
        final SecurityLogRepository securityLogRepository,
        final AuthUserRepository authUserRepository
    ) {
        this.securityLogRepository = securityLogRepository;
        this.authUserRepository = authUserRepository;
    }

    public Optional<String> resolve(final String identifier) {
        return Optional.ofNullable(identifier)
            .map(securityLogRepository::findBySessionIdOrderByEventTimeDesc)
            .map(this::getUserFromSecurityLogs)
            .map(AuthUser::getUserId);
    }

    private AuthUser getUserFromSecurityLogs(final List<SecurityLog> logs) {
        // Are there log entries for the session? Has the session been logged out?
        if (logs.isEmpty() ||
            logs.stream().anyMatch(l -> l.getEventTypeCd().equals(SecurityEventType.LOGOUT))) {
            return null;
        } else {
            var entryId = logs.get(0).getNedssEntryId();
            return authUserRepository.findByNedssEntryId(entryId).orElse(null);
        }
    }

}
