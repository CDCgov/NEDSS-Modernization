package gov.cdc.nbs.support.concept.condition;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class ConditionParameterResolver {

  private static final int CODE_COLUMN = 1;
  private static final String QUERY = """

      select
          condition_cd        as [value]
      from NBS_SRTE..Condition_code
          where condition_short_nm = ?
      """;
  private static final int DESCRIPTION_INDEX = 1;

  private final JdbcTemplate template;

  ConditionParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<String> resolve(final String value) {
    return this.template.query(
        QUERY,
        statement -> {
          statement.setString(DESCRIPTION_INDEX, value);
        },
        (rs, row) -> rs.getString(CODE_COLUMN)
    ).stream().findFirst();
  }
}
