package gov.cdc.nbs.option.regions.list;

import gov.cdc.nbs.option.Option;
import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RegionListFinder {
  private final JdbcTemplate template;
  private final RegionListRowMapper mapper;

  private static final String QUERY =
      """
      select
          code                as [value],
          code_desc_txt       as [name]
      from [NBS_SRTE].[dbo].state_county_code_value
      where code_system_nm = 'FIPS_9REGIONS'
      order by
          indent_level_nbr,
          code_desc_txt
      """;

  RegionListFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new RegionListRowMapper();
  }

  public Collection<Option> all() {
    return this.template.query(QUERY, statement -> {}, mapper);
  }
}
