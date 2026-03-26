package gov.cdc.nbs.questionbank.page;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class JDBCPageNameVerifier implements PageNameVerifier {

  private static final String QUERY =
      """
      select
      count(*)
      from WA_template
      where template_nm = ?
      """;

  private final JdbcTemplate template;

  JDBCPageNameVerifier(final JdbcTemplate template) {
    this.template = template;
  }

  @Override
  public boolean isUnique(final String name) {
    return this.template
        .query(QUERY, statement -> statement.setString(1, name), (rs, row) -> rs.getLong(1))
        .stream()
        .filter(found -> found > 0)
        .findFirst()
        .isEmpty();
  }
}
