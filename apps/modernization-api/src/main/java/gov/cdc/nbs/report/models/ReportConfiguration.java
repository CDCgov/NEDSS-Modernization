package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ReportConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportDataSource dataSource,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Library reportLibrary,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<FilterConfiguration> filters,
    List<ReportColumn> reportColumns) {

  public boolean isPython() {
    return runner().equalsIgnoreCase("python");
  }
}
