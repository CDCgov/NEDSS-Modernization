package gov.cdc.nbs.questionbank.condition;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class ConditionParameterResolver {

  private static final String QUERY =
      """
      select
          condition_cd
      from NBS_SRTE..Condition_code
      where condition_short_nm = ?
      """;

  private final JdbcTemplate template;

  ConditionParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<String> resolve(final String value) {
    return this.template
        .query(QUERY, statement -> statement.setString(1, value), (rs, row) -> rs.getString(1))
        .stream()
        .findFirst();
  }
}
