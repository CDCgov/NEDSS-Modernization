package gov.cdc.nbs.option.report.distinct;

import gov.cdc.nbs.datasource.utils.DataSourceNameConfiguration;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.option.Option;
import java.util.Collection;
import java.util.Map;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class DistinctValuesFinder {

  private static final String DATA_QUERY =
      """
      select distinct ? as [value]
      from ?
      order by
          value
      """;

  private static final String DATA_SOURCE_QUERY =
      """
        select
            ds.data_source_name,
            c.column_name
        from NBS_ODSE..Data_Source ds
        inner join NBS_ODSE..Data_source_column c on c.data_source_uid = ds.data_source_uid
        where
            c.column_uid = ?
    """;

  private final JdbcClient client;
  private final DataSourceNameUtils dataSourceNameUtils;

  DistinctValuesFinder(final JdbcClient client, final DataSourceNameConfiguration config) {
    this.client = client;
    this.dataSourceNameUtils = new DataSourceNameUtils(config);
  }

  Collection<Option> find(final String columnUid) {
    Map<String, Object> column =
        this.client.sql(DATA_SOURCE_QUERY).param(columnUid).query().singleRow();

    String columnName = column.get("column_name").toString();
    String dataSourceName =
        dataSourceNameUtils.buildDataSourceName(column.get("data_source_name").toString());

    return this.client
        .sql(DATA_QUERY)
        .param(columnName)
        .param(dataSourceName)
        .query()
        .singleColumn()
        .stream()
        .map(o -> new Option(o.toString()))
        .toList();
  }
}
