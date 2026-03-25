package gov.cdc.nbs.option.county;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CountyStateParameterResolver {

  private static final int CODE_COLUMN = 1;
  private static final String QUERY =
      """

      select
          state_cd
      from NBS_SRTE..State_code
          where code_desc_txt = ?
      """;
  private static final int DESCRIPTION_INDEX = 1;

  private final JdbcTemplate template;

  CountyStateParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  public Optional<String> resolve(final String value) {
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
