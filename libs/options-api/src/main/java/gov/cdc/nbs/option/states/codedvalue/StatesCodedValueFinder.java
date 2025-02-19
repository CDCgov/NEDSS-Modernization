package gov.cdc.nbs.option.states.codedvalue;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class StatesCodedValueFinder {
  private final JdbcTemplate template;
  private final StatesCodedValueRowMapper mapper;

  private static final String QUERY =
      """
              SELECT
                state_cd as value, state_nm as name, code_desc_txt as abbreviation
              FROM
                NBS_SRTE.dbo.state_code
          order by
              indent_level_nbr
          """;

  StatesCodedValueFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new StatesCodedValueRowMapper();
  }

  public Collection<StateCodedValue> all() {
    return this.template.query(
        QUERY, statement -> {
        }, mapper);
  }

}
