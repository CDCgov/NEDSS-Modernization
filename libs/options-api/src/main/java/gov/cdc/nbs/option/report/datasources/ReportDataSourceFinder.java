package gov.cdc.nbs.option.report.datasources;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReportDataSourceFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
        data_source_uid     as [value],
        data_source_name    as [name],
        data_source_title   as [label]
      from NBS_ODSE..Data_Source
      order by
          [name]
      """;

  ReportDataSourceFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}
