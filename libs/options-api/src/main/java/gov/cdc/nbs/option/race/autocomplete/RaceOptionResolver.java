package gov.cdc.nbs.option.race.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class RaceOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from NBS_SRTE..Code_value_general
      where code_set_nm ='RACE_CALCULATED'
          and code_short_desc_txt like :criteria
      order by
          code_short_desc_txt
      offset 0 rows
      fetch next :limit rows only
      """;

  RaceOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
