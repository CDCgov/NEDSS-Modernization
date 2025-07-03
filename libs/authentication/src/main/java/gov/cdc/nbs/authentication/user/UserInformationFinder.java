package gov.cdc.nbs.authentication.user;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class UserInformationFinder {

  private static final String QUERY = """
      select
          [user].[nedss_entry_id] as identifier,
          [user].[user_first_nm] as first,
          [user].[user_last_nm] as last,
          [user].user_id as username,
          case record_status_cd
              when 'ACTIVE' then 1
              else 0
          end as enabled
      from Auth_user [user]
      where [user].[user_id] = :username
        """;

  private final JdbcClient client;

  UserInformationFinder(final JdbcClient client) {
    this.client = client;

  }

  Optional<UserInformation> find(final String username) {
    return this.client.sql(QUERY)
        .param("username", username)
        .query(UserInformation.class)
        .optional();
  }
}
