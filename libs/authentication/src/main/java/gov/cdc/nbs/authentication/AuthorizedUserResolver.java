package gov.cdc.nbs.authentication;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthorizedUserResolver {

  private static final String QUERY =
      """
      select
          [user].user_id
      from Security_log [log]

          join [Auth_user] [user] on
                  [user].nedss_entry_id = [log].nedss_entry_id
              and [user].record_status_cd = 'ACTIVE'

      where [log].[session_id] = ?
      and [log].event_time = (
          select
              max(event_time)
          from Security_log [eff_log]
          where [eff_log].session_id = [log].[session_id]
      )
      and [log].event_type_cd = 'LOGIN_SUCCESS'
      """;
  private static final int USER_NAME_COLUMN = 1;
  private static final int SESSION_PARAMETER = 1;

  private final JdbcTemplate template;

  AuthorizedUserResolver(final JdbcTemplate template) {
    this.template = template;
  }

  public Optional<String> resolve(final String identifier) {

    return template
        .query(
            QUERY,
            statement -> statement.setString(SESSION_PARAMETER, identifier),
            (rs, row) -> rs.getString(USER_NAME_COLUMN))
        .stream()
        .findFirst();
  }
}
