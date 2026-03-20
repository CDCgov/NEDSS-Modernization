package gov.cdc.nbs.report;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportSpecGenerator {

  public ReportSpec generate(List<Long> columnUids) {


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
