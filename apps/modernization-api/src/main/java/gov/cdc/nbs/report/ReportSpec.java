package gov.cdc.nbs.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.Map;

public record ReportSpec(
    @JsonProperty(value = "version", required = true) int version,
    @JsonProperty(value = "is_export", required = true) boolean isExport,
    @JsonProperty(value = "is_builtin", required = true) boolean isBuiltin,
    @JsonProperty(value = "report_title", required = true) String reportTitle,
    @JsonProperty(value = "library_name", required = true) String libraryName,
    @JsonProperty(value = "data_source_name", required = true) String dataSourceName,
    @JsonProperty(value = "subset_query", required = true) String subsetQuery,
    @JsonProperty(value = "time_range") Map<String, LocalDate> timeRange) {

  public ReportSpec {
    if (timeRange != null) {
      if (!timeRange.containsKey("start") || !timeRange.containsKey("end")) {
        throw new IllegalArgumentException("time_range must contain 'start' and 'end' keys");
      }
    }
  }
}
