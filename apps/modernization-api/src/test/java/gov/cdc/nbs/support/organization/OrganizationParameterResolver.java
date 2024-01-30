package gov.cdc.nbs.support.organization;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class OrganizationParameterResolver {

  private static final int IDENTIFIER_COLUMN = 1;
  private static final String QUERY = """
      select
          organization_uid
      from Organization
      where display_nm = ?
      """;
  private static final int NAME_INDEX = 1;

  private final JdbcTemplate template;

  OrganizationParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<Long> resolve(final String value) {
    return this.template.query(
        QUERY,
        statement -> {
          statement.setString(NAME_INDEX, value);
        },
        (rs, row) -> rs.getLong(IDENTIFIER_COLUMN)
    ).stream().findFirst();
  }
}
