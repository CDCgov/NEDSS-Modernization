package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ReportConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportDataSource dataSource,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Library library,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String title,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Character shared,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<BasicFilterConfiguration> basicFilters,
    AdvancedFilterConfiguration advancedFilter,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<ReportColumn> columns,
    List<Long> defaultColumnUids,
    SortSpec defaultSort) {

  @JsonIgnore
  public boolean isPython() {
    return library().runner().equalsIgnoreCase("python");
  }
}
