package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public record ReportExecutionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long reportUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long dataSourceUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean isExport,
    String reportTitle,
    List<Long> columnUids,
    List<Filter> filters,
    TimeRange timeRange) {

  public record TimeRange(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String start,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String end) {

    public TimeRange {
      if (start.isBlank() || end.isBlank()) {
        throw new IllegalArgumentException("time_range must contain 'start' and 'end' keys");
      }

      try {
        LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE);
      } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Start and end dates must be in the format YYYY-MM-DD");
      }
    }
  }
}
