package gov.cdc.nbs.option.condition;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class ConditionOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
          condition_cd        as [value],
          condition_short_nm  as [name],
          indent_level_nbr    as [order]
      from NBS_SRTE..Condition_code
          where status_cd = 'A'
      order by
          indent_level_nbr,
          condition_short_nm
      """;

  ConditionOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}
