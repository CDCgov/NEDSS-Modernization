package gov.cdc.nbs.option.jurisdictions.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class JurisdictionOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from [NBS_SRTE].[dbo].Jurisdiction_code
      where   status_cd = 'A'
          and code_short_desc_txt like :criteria
      order by
          indent_level_nbr
      offset 0 rows
      fetch next :limit rows only
      """;

  JurisdictionOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
