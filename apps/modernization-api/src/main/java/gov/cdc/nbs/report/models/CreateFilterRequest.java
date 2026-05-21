package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CreateFilterRequest(
    List<Long> columnUids, List<BasicFilter> basicFilters, AdvancedQuery advancedQuery) {
  public record BasicFilter(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
          Long filterCodeUid, //  Pertains to FilterCode.ID
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long columnUid,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
          Boolean includeNulls, //  Pertains to ReportFilterValidation
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportConstants.FilterType type,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isRequired) {}
}
