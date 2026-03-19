package gov.cdc.nbs.report;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;

public record ReportConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Map<String, Long> id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner) {

  private static Map<String, Long> createReportId(long reportUid, Long dataSourceUid) {
    Map<String, Long> idMap = new HashMap<>();
    idMap.put("reportUid", reportUid);
    idMap.put("dataSourceUid", dataSourceUid);
    return idMap;
  }

  public ReportConfiguration(Long reportUid, Long dataSourceUid, String runner) {
    this(createReportId(reportUid, dataSourceUid), runner);
  }
}
