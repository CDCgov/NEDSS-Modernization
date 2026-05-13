package gov.cdc.nbs.option.jurisdictions.list;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class JurisdictionOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from NBS_SRTE..Jurisdiction_code
      where   status_cd = 'A'
      order by
          indent_level_nbr
      """;

  JurisdictionOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}
