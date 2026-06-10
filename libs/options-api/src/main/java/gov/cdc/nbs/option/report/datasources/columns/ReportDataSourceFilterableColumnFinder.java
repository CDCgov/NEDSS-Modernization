package gov.cdc.nbs.option.report.datasources.columns;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.option.jdbc.OptionRowMapper;
import java.util.Collection;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class ReportDataSourceFilterableColumnFinder {

  private final JdbcClient client;
  private final RowMapper<Option> mapper;

  private static final String QUERY =
      """
      select
        column_uid         as [value],
        column_name        as [name],
        column_title       as [label]
      from NBS_ODSE..Data_source_column
      where data_source_uid = ?
        and filterable = 'Y'
      order by
          [name]
      """;

  ReportDataSourceFilterableColumnFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new OptionRowMapper();
  }

  public Collection<Option> find(final String dataSourceUid) {
    return this.client.sql(QUERY).param(Long.valueOf(dataSourceUid)).query(this.mapper).list();
  }
}
