package gov.cdc.nbs.testing.authorization.jurisdiction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
class JurisdictionParameterResolver {

  private static final int IDENTIFIER_COLUMN = 1;
  private static final int CODE_COLUMN = 2;
  private static final String QUERY = """
      select
          nbs_uid,
          code
      from NBS_SRTE.[dbo].Jurisdiction_code
      where code_desc_txt = ?
      """;
  private static final int NAME_INDEX = 1;

  private final JdbcTemplate template;

  JurisdictionParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<JurisdictionIdentifier> resolve(final String value) {
    return this.template.query(
        QUERY,
        statement -> {
          statement.setString(NAME_INDEX, value);
        },
        this::map
    ).stream().findFirst();
  }

  private JurisdictionIdentifier map(final ResultSet resultSet, final int row) throws SQLException {
    long identifier = resultSet.getLong(IDENTIFIER_COLUMN);
    String code = resultSet.getString(CODE_COLUMN);

    return new JurisdictionIdentifier(identifier, code);
  }
}
