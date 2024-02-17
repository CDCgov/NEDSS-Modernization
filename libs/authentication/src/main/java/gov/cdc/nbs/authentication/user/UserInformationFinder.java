package gov.cdc.nbs.authentication.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class UserInformationFinder {

  private static final String QUERY = """
    select
        [user].[nedss_entry_id],
        [user].[user_first_nm],
        [user].[user_last_nm],
        [user].user_id,
        case record_status_cd
            when 'ACTIVE' then 1
            else 0
        end
    from Auth_user [user]
    where [user].[user_id] = ?
      """;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int FIRST_COLUMN = 2;
  private static final int LAST_COLUMN = 3;
  private static final int USERNAME_COLUMN = 4;
  private static final int ENABLED_COLUMN = 5;
  private static final int USERNAME_PARAMETER = 1;

  private final JdbcTemplate template;
  private final RowMapper<UserInformation> mapper;

  UserInformationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new UserInformationRowMapper(
        new UserInformationRowMapper.Column(
            IDENTIFIER_COLUMN,
            FIRST_COLUMN,
            LAST_COLUMN,
            USERNAME_COLUMN,
            ENABLED_COLUMN
        )
    );
  }

  Optional<UserInformation> find(final String username) {
    return this.template.queryForStream(
        QUERY,
        statement -> statement.setString(USERNAME_PARAMETER, username),
        this.mapper
    ).findFirst();
  }
}
