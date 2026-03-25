package gov.cdc.nbs.questionbank.page;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class JDBCDatamartNameVerifier implements DatamartNameVerifier {

  private static final String QUERY =
      """
      select
      count(*)
      from WA_template
      where datamart_nm = ?
      """;

  private final JdbcTemplate template;

  JDBCDatamartNameVerifier(final JdbcTemplate template) {
    this.template = template;
  }

  @Override
  public boolean isUnique(final String datamart) {
    return this.template
        .query(QUERY, statement -> statement.setString(1, datamart), (rs, row) -> rs.getLong(1))
        .stream()
        .filter(found -> found > 0)
        .findFirst()
        .isEmpty();
  }
}
