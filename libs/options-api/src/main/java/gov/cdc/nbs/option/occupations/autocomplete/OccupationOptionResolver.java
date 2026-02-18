package gov.cdc.nbs.option.occupations.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class OccupationOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      select
          code                                as [value],
          code_short_desc_txt                 as [name],
          row_number() over (order by [code]) as [order]
      from NBS_SRTE..NAICS_Industry_code
      where   status_cd = 'A'
          and parent_is_cd = 'root'
          and code_short_desc_txt like :criteria
      order by
          code_short_desc_txt
      offset 0 rows
      fetch next :limit rows only
      """;

  OccupationOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
