package gov.cdc.nbs.testing.authorization.jurisdiction;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
class JurisdictionParameterResolver {

  private static final String QUERY = """
      select
          nbs_uid,
          code,
          code_desc_txt
      from NBS_SRTE.[dbo].Jurisdiction_code
      where code_desc_txt = ?
      """;

  private static final int IDENTIFIER_COLUMN = 1;
  private static final int CODE_COLUMN = 2;
  private static final int NAME_COLUMN = 3;

  private final JdbcClient client;

  JurisdictionParameterResolver(final JdbcClient client) {
    this.client = client;
  }

  Optional<JurisdictionIdentifier> resolve(final String value) {
    return this.client.sql(QUERY)
        .param(value)
        .query(this::map)
        .optional();
  }

  private JurisdictionIdentifier map(final ResultSet resultSet, final int row) throws SQLException {
    long identifier = resultSet.getLong(IDENTIFIER_COLUMN);
    String code = resultSet.getString(CODE_COLUMN);
    String name = resultSet.getString(NAME_COLUMN);

    return new JurisdictionIdentifier(identifier, code, name);
  }
}
