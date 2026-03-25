package gov.cdc.nbs.testing.authorization;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("java:S100")
class ActiveUserParameterResolver {

  private static final String QUERY =
      """
      select
          auth_user_uid,
          user_id,
          nedss_entry_id
      from Auth_user
      where user_id = ?
      """;

  private final JdbcTemplate template;

  ActiveUserParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<ActiveUser> resolve(final String value) {
    return this.template
        .query(QUERY, statement -> statement.setString(1, value), this::map)
        .stream()
        .findFirst();
  }

  private ActiveUser map(final ResultSet resultSet, final int row) throws SQLException {
    long identifier = resultSet.getLong(1);
    String username = resultSet.getString(2);
    long nedss = resultSet.getLong(3);

    return new ActiveUser(identifier, username, nedss);
  }
}
