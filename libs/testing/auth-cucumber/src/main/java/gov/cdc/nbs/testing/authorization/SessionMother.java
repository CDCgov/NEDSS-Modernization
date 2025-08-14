package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.authentication.enums.SecurityEventType;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@ScenarioScope
class SessionMother {

  private static final String INSERT = """
      insert into Security_log(
          security_log_uid,
          user_id,
          event_type_cd,
          event_time,
          session_id,
          nedss_entry_id,
          first_nm,
          last_nm
      )
      select
          coalesce(max([log].security_log_uid), 0) + 1,
          :user,
          :type,
          :time,
          :session,
          :entry,
          'Feature',
          'Testing'
      FROM security_log [log]
      """;

  private static final String DELETE = """
      delete from security_log
      where session_id in (:identifiers)
      """;

  private final Active<SessionCookie> active;
  private final JdbcClient client;
  private final TestingDataCleaner<String> cleaner;

  SessionMother(final Active<SessionCookie> active, JdbcClient client) {
    this.active = active;
    this.client = client;
    this.cleaner = new TestingDataCleaner<>(client, DELETE, "identifiers");
  }

  @PreDestroy
  void reset() {
    this.cleaner.clean();
  }

  void create(final ActiveUser user) {
    SessionCookie session = login(user);
    active.active(session);
  }

  private SessionCookie login(final ActiveUser user) {
    String sessionId = UUID.randomUUID()
        .toString()
        .replace("-", "");

    log(user, sessionId, SecurityEventType.LOGIN_SUCCESS);

    return new SessionCookie(sessionId);
  }

  private void log(
      final ActiveUser user,
      final String session,
      final SecurityEventType type
  ) {
    // insert security_log event with sessionId
    this.client.sql(INSERT)
        .param("user", user.username())
        .param("type", type.name())
        .param("time", LocalDateTime.now())
        .param("session", session)
        .param("entry", user.nedssEntry())
        .update();
  }

  public void logout(final ActiveUser current, final String session) {
    log(current, session, SecurityEventType.LOGOUT);
  }
}
