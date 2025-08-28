package gov.cdc.nbs.option.counties.list;


import gov.cdc.nbs.option.Option;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CountiesListFinder {

  private static final String QUERY = """
      select
          code                as [value],
          code_desc_txt       as [name],
          indent_level_nbr    as [order]
      from [NBS_SRTE].[dbo].state_county_code_value
      where parent_is_cd = ?
      order by
          indent_level_nbr,
          code_desc_txt
      """;

  private final JdbcClient client;
  private final RowMapper<Option> mapper;

  CountiesListFinder(final JdbcClient client) {
    this.client = client;

    this.mapper = new CountiesListRowMapper();
  }

  Collection<Option> find(final String state) {
    return this.client.sql(QUERY)
        .param(state)
        .query(this.mapper)
        .list();
  }
}
