package gov.cdc.nbs.option.condition.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class ConditionOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      select
          condition_cd        as [value],
          condition_short_nm  as [name],
          indent_level_nbr    as [order]
      from [NBS_SRTE].[dbo].Condition_code
      where   status_cd = 'A'
          and condition_short_nm like :criteria

      order by
          condition_short_nm

      offset 0 rows
      fetch next :limit rows only
      """;

  ConditionOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
