package gov.cdc.nbs.option.states.list;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.option.Option;
import java.util.Collection;

@Component
public class StateListFinder {
  private final JdbcTemplate template;
  private final StateListRowMapper mapper;

  private static final String QUERY =
      """
              SELECT
                state_cd as value, state_nm as name
              FROM
                NBS_SRTE.dbo.state_code
          order by
              indent_level_nbr
          """;

  StateListFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new StateListRowMapper();
  }

  public Collection<Option> all() {
    return this.template.query(
        QUERY, statement -> {
        }, mapper);
  }

}
