package gov.cdc.nbs.report;

import org.springframework.stereotype.Component;

@Component
public class ReportSpecGenerator {

  public ReportSpec generate() {
    return new ReportSpec(
        1,
        true,
        true,
        "Test Report",
        "nbs_custom",
        "nbs_rdb.investigation",
        "SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]",
        null);
  }
}
