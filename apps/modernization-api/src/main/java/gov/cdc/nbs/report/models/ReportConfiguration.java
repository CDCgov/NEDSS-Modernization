package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner) {

  @JsonIgnore
  public boolean isPython() {
    return runner().equalsIgnoreCase("python");
  }
}
