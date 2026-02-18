package gov.cdc.nbs.option.occupations;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class OccupationOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
          code                                as [value],
          code_short_desc_txt                 as [name],
          row_number() over (order by [code]) as [order]
      from NBS_SRTE..NAICS_Industry_code
      where status_cd = 'A'
        and parent_is_cd = 'root'
      order by
        code_short_desc_txt
      """;

  OccupationOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}
