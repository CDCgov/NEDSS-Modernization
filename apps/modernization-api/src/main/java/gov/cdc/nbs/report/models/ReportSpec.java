package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record ReportSpec(
    @JsonProperty(value = "version", required = true) int version,
    @JsonProperty(value = "is_export", required = true) boolean isExport,
    @JsonProperty(value = "is_builtin", required = true) boolean isBuiltin,
    @JsonProperty(value = "report_title", required = true) String reportTitle,
    @JsonProperty(value = "library_name", required = true) String libraryName,
    @JsonProperty(value = "data_source_name", required = true) String dataSourceName,
    @JsonProperty(value = "subset_query", required = true) String subsetQuery,
    @JsonProperty(value = "time_range") TimeRange timeRange) {

  public record TimeRange(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) LocalDate start,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) LocalDate end) {

    public TimeRange {
      if (start.isAfter(end)) {
        throw new IllegalArgumentException("Start date cannot be after end date");
      }
    }
  }
}
