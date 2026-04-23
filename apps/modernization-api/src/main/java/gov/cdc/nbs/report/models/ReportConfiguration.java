package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ReportConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportDataSource dataSource,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Library reportLibrary,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String reportTitle,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<BasicFilterConfiguration> basicFilters,
    AdvancedFilterConfiguration advancedFilter,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<ReportColumn> reportColumns) {

  @JsonIgnore
  public boolean isPython() {
    return runner().equalsIgnoreCase("python");
  }
}
