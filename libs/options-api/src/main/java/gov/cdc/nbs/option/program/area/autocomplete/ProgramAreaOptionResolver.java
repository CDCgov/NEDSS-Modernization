package gov.cdc.nbs.option.program.area.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class ProgramAreaOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
      select
          prog_area_cd        as [value],
          prog_area_desc_txt  as [name],
          nbs_uid             as [order]
      from [NBS_SRTE].[dbo].Program_area_code
      where   status_cd = 'A'
          and prog_area_desc_txt like :criteria
      order by
          nbs_uid
      offset 0 rows
      fetch next :limit rows only
      """;

  ProgramAreaOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
