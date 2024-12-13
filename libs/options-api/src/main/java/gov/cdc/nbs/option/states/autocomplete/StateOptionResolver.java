package gov.cdc.nbs.option.states.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class StateOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY = """
      select
          state_cd            as [value],
          code_desc_txt       as [name],
          indent_level_nbr    as [order]
      from
          [NBS_SRTE].[dbo].State_code
      where
          code_desc_txt like :criteria
      order by
          indent_level_nbr
      offset 0 rows
      fetch next :limit rows only
      """;

  StateOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
