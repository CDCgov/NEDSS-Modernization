package gov.cdc.nbs.option.race.detailed.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class DetailedRaceOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY = """
      select
          code                                as [value],
          code_short_desc_txt                 as [name],
          row_number() over (order by [code]) as [order]
      from NBS_SRTE..Race_code
      where   code_short_desc_txt like :criteria
          and parent_is_cd =:root
      order by
          code_short_desc_txt
      offset 0 rows
      fetch next :limit rows only
      """;

  DetailedRaceOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
