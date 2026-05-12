package gov.cdc.nbs.option.report.distinct;

import gov.cdc.nbs.datasource.utils.DataSourceNameConfiguration;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import java.util.Collection;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class DistinctValuesFinder {

  private static final String QUERY =
      """
      select distinct ? as [value]
      from ?
      order by
          value
      """;

  private final JdbcClient client;
  private final DataSourceColumnRepository repository;
  private final DataSourceNameUtils dataSourceNameUtils;

  DistinctValuesFinder(
      final JdbcClient client,
      final DataSourceNameConfiguration config,
      final DataSourceColumnRepository repository) {
    this.client = client;
    this.repository = repository;
    this.dataSourceNameUtils = new DataSourceNameUtils(config);
  }

  Collection<Option> find(final String columnUid) {
    DataSourceColumn column =
        repository
            .findById(Long.parseLong(columnUid))
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Unknown report column ID: %s".formatted(columnUid)));
    String columnName = column.getColumnName();
    String dataSourceName =
        dataSourceNameUtils.buildDataSourceName(column.getDataSource().getDataSourceName());

    return this.client
        .sql(QUERY)
        .param(columnName)
        .param(dataSourceName)
        .query()
        .singleColumn()
        .stream()
        .map(o -> new Option(o.toString()))
        .toList();
  }
}
