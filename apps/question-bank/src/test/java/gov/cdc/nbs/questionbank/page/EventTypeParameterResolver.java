package gov.cdc.nbs.questionbank.page;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EventTypeParameterResolver {

  private static final String QUERY = """
      select
          code
      from NBS_SRTE..code_value_general
      where code_set_nm = 'BUS_OBJ_TYPE'
        and code_short_desc_txt = ?
      """;

  private final JdbcTemplate template;

  EventTypeParameterResolver(final JdbcTemplate template) {
    this.template = template;
  }

  Optional<String> resolve(final String value) {
    return this.template.query(
        QUERY,
        statement -> statement.setString(1, value),
        (rs, row) -> rs.getString(1)
    ).stream().findFirst();
  }
}
