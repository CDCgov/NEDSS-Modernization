package gov.cdc.nbs.option.report.sections;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReportSectionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select
        section_code        as [value],
        desc_txt            as [name],
        0                   as [order]
      from NBS_ODSE..Report_Section
      order by
          [name]
      """;

  ReportSectionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}
