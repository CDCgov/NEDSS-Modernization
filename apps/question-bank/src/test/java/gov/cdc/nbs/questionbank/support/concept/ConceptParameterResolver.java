package gov.cdc.nbs.questionbank.support.concept;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class ConceptParameterResolver {

  private static final String QUERY = """
      select
          code
      from NBS_SRTE..code_value_general
      where code_set_nm = ?
        and code_short_desc_txt = ?
      """;

  private final JdbcTemplate template;

  ConceptParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<String> resolve(final String set, final String value) {
    return this.template.query(
        QUERY,
        statement -> {
          statement.setString(1, set);
          statement.setString(2, value);
        },
        (rs, row) -> rs.getString(1)
    ).stream().findFirst();
  }
}
