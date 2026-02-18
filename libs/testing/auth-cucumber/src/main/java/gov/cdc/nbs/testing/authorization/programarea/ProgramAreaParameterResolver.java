package gov.cdc.nbs.testing.authorization.programarea;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class ProgramAreaParameterResolver {

  private static final String QUERY =
      """
      select
          nbs_uid,
          prog_area_cd,
          prog_area_desc_txt
      from NBS_SRTE.[dbo].Program_area_code
      where prog_area_desc_txt = ?
      """;

  private static final int IDENTIFIER_COLUMN = 1;
  private static final int CODE_COLUMN = 2;
  private static final int NAME_COLUMN = 3;

  private final JdbcClient client;

  ProgramAreaParameterResolver(final JdbcClient client) {
    this.client = client;
  }

  Optional<ProgramAreaIdentifier> resolve(final String value) {
    return client.sql(QUERY).param(value).query(this::map).optional();
  }

  private ProgramAreaIdentifier map(final ResultSet resultSet, final int row) throws SQLException {
    long identifier = resultSet.getLong(IDENTIFIER_COLUMN);
    String code = resultSet.getString(CODE_COLUMN);
    String name = resultSet.getString(NAME_COLUMN);

    return new ProgramAreaIdentifier(identifier, code, name);
  }
}
