package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.authentication.enums.SecurityEventType;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
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
  private final Available<SessionCookie> available;
  private final NamedParameterJdbcTemplate template;

  SessionMother(
      final Active<SessionCookie> active,
      final JdbcTemplate template
  ) {
    this.active = active;
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = new Available<>();
  }

  @PostConstruct
  void reset() {
    List<String> identifiers = this.available.all()
        .map(SessionCookie::identifier)
        .toList();

    if (!identifiers.isEmpty()) {
      Map<String, List<String>> parameters = Map.of("identifiers", identifiers);

      template.execute(
          DELETE,
          new MapSqlParameterSource(parameters),
          PreparedStatement::executeUpdate
      );

      this.available.reset();
    }

    this.active.reset();
  }

  void create(final ActiveUser user) {
    SessionCookie session = login(user);

    available.available(session);
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
    Map<String, ? extends Serializable> parameters = Map.of(
        "user", user.username(),
        "type", type.name(),
        "time", Timestamp.from(Instant.now()),
        "session", session,
        "entry", user.nedssEntry()
    );

    this.template.execute(
        INSERT,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate
    );

  }

  public void logout(final ActiveUser current, final String session) {
    log(current, session, SecurityEventType.LOGOUT);
  }
}
