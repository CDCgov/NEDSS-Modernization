package gov.cdc.nbs.option.report.libraries;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReportLibraryFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
        library_uid    as [value],
        library_name   as [name],
        desc_txt       as [label]
      from NBS_ODSE..Report_Library
      order by
          [name]
      """;

  ReportLibraryFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}
