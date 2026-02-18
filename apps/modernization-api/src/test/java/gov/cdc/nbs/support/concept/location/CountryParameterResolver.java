package gov.cdc.nbs.support.concept.location;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class CountryParameterResolver {

  private static final int CODE_COLUMN = 1;
  private static final String QUERY =
      """

      select
          code
      from NBS_SRTE..Country_code
          where code_desc_txt = ?
      """;
  private static final int DESCRIPTION_INDEX = 1;

  private final JdbcTemplate template;

  CountryParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<String> resolve(final String value) {
    return this.template
        .query(
            QUERY,
            statement -> {
              statement.setString(DESCRIPTION_INDEX, value);
            },
            (rs, row) -> rs.getString(CODE_COLUMN))
        .stream()
        .findFirst();
  }
}
