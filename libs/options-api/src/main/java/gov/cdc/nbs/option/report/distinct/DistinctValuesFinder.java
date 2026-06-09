package gov.cdc.nbs.option.report.distinct;

import gov.cdc.nbs.datasource.utils.DataSourceNameConfiguration;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.option.Option;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class DistinctValuesFinder {

  private static final System.Logger LOGGER =
      System.getLogger(DistinctValuesFinder.class.getName());

  private static final String DATA_QUERY =
      """
      select distinct [%s] as [value]
      from %s
      where [%s] is not NULL
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
        inner join NBS_ODSE..Data_Source_Codeset cd on cd.column_uid = c.column_uid
        where
            c.column_uid = ?
            AND cd.code_desc_cd = 'H'
    """;

  private final JdbcClient client;
  private final DataSourceNameUtils dataSourceNameUtils;

  DistinctValuesFinder(final JdbcClient client, final DataSourceNameConfiguration config) {
    this.client = client;
    this.dataSourceNameUtils = new DataSourceNameUtils(config);
  }

  Collection<Option> find(final String columnUid) {
    try {
      Map<String, Object> column =
          this.client.sql(DATA_SOURCE_QUERY).param(Long.valueOf(columnUid)).query().singleRow();

      String columnName = column.get("column_name").toString();
      String dataSourceName =
          dataSourceNameUtils.buildDataSourceName(column.get("data_source_name").toString());

      return this.client
          .sql(DATA_QUERY.formatted(columnName, dataSourceName, columnName))
          .query()
          .singleColumn()
          .stream()
          .map(o -> new Option(o.toString()))
          .toList();

    } catch (NumberFormatException | EmptyResultDataAccessException e) {
      // report column uid not a number or  column not found
      LOGGER.log(
          System.Logger.Level.INFO,
          "Report column UID %s not found or not configured".formatted(columnUid));
      return List.of();
    }
  }
}
