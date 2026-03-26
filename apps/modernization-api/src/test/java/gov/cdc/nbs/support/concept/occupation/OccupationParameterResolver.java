package gov.cdc.nbs.support.concept.occupation;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class OccupationParameterResolver {

  private static final String QUERY =
      """
      select
          code
      from NBS_SRTE..NAICS_Industry_code
      where code_short_desc_txt = ?
      """;

  private final JdbcTemplate template;

  OccupationParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<String> resolve(final String value) {
    return this.template
        .query(
            QUERY,
            statement -> {
              statement.setString(1, value);
            },
            (rs, row) -> rs.getString(1))
        .stream()
        .findFirst();
  }
}
