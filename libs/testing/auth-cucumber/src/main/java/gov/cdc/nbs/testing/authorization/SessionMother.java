package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.authentication.entity.SecurityLog;
import gov.cdc.nbs.authentication.enums.SecurityEventType;
import gov.cdc.nbs.authentication.repository.SecurityLogRepository;
import gov.cdc.nbs.testing.support.Active;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
class SessionMother {

  private final SecurityLogRepository repository;
  private final Active<SessionCookie> session;

  SessionMother(
      final SecurityLogRepository repository,
      final Active<SessionCookie> session
  ) {
    this.repository = repository;
    this.session = session;
  }

  void reset() {
    this.session.reset();
  }

  void create(final ActiveUser user) {
    // insert security_log event with sessionId
    String sessionId = UUID.randomUUID()
        .toString()
        .replace("-", "");

    SecurityLog log = new SecurityLog();
    log.setId(repository.getMaxId() + 1);
    log.setEventTypeCd(SecurityEventType.LOGIN_SUCCESS);
    log.setEventTime(Instant.now());
    log.setSessionId(sessionId);
    log.setNedssEntryId(user.nedssEntry());
    log.setFirstNm("Integration");
    log.setLastNm("Testing");
    repository.save(log);

    session.active(new SessionCookie(sessionId));
  }
}
