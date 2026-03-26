package gov.cdc.nbs.testing.support.concept;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConceptParameterResolver {

  private static final String QUERY =
      """
      select
          code
      from NBS_SRTE..code_value_general
      where code_set_nm = ?
        and code_short_desc_txt = ?
      """;

  private final JdbcTemplate template;

  public ConceptParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  public Optional<String> resolve(final String set, final String value) {
    return this.template
        .query(
            QUERY,
            statement -> {
              statement.setString(1, set);
              statement.setString(2, value);
            },
            (rs, row) -> rs.getString(1))
        .stream()
        .findFirst();
  }
}
