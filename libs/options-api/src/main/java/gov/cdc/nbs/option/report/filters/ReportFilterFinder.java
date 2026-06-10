package gov.cdc.nbs.option.report.filters;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReportFilterFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
        filter_uid     as [value],
        filter_name    as [name]
      from NBS_ODSE..Filter_code
      order by
          [name]
      """;

  ReportFilterFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}
