package gov.cdc.nbs.option.counties.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class CountyOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY = """
      select
          code                as [value],
          code_desc_txt       as [name],
          indent_level_nbr    as [order]
      from
          [NBS_SRTE].[dbo].state_county_code_value
      where
          code_desc_txt like :criteria
          and parent_is_cd =:param2
      order by
          indent_level_nbr
      offset 0 rows
      fetch next :limit rows only
      """;

  CountyOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
