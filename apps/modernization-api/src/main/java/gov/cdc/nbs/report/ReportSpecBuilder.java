package gov.cdc.nbs.report;

import gov.cdc.nbs.report.models.ReportSpec;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportSpecBuilder {

  public ReportSpec build() {
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
