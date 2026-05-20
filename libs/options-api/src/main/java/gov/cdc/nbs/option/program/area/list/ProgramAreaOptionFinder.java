package gov.cdc.nbs.option.program.area.list;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class ProgramAreaOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
          prog_area_cd        as [value],
          prog_area_desc_txt  as [name],
          nbs_uid             as [order]
      from NBS_SRTE..Program_area_code
      where   status_cd = 'A'
      order by
          nbs_uid
      """;

  ProgramAreaOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}
