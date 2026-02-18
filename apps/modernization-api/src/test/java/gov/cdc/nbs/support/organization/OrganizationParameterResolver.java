package gov.cdc.nbs.support.organization;

import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class OrganizationParameterResolver {

  private static final int IDENTIFIER_COLUMN = 1;
  private static final String QUERY =
      """
      select
          organization_uid
      from Organization
      where display_nm = ?
      """;

  private final JdbcClient client;

  OrganizationParameterResolver(final JdbcClient client) {
    this.client = client;
  }

  Optional<OrganizationIdentifier> resolve(final String value) {
    return this.client
        .sql(QUERY)
        .param(value)
        .query((rs, row) -> new OrganizationIdentifier(rs.getLong(IDENTIFIER_COLUMN)))
        .optional();
  }
}
