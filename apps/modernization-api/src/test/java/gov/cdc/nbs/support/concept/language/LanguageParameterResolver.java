package gov.cdc.nbs.support.concept.language;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class LanguageParameterResolver {

  private static final String QUERY = """
      select
          code
      from NBS_SRTE..Language_code
      where code_short_desc_txt = ?
      """;

  private final JdbcTemplate template;

  LanguageParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<String> resolve(final String value) {
    return this.template.query(
        QUERY,
        statement -> {
          statement.setString(1, value);
        },
        (rs, row) -> rs.getString(1)
    ).stream().findFirst();
  }
}
