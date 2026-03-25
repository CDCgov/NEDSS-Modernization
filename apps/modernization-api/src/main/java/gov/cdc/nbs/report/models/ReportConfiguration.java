package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReportConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String dataSourceName) {

  public boolean isPython() {
    return runner().equalsIgnoreCase("python");
  }
}
