package gov.cdc.nbs.report.models;

import gov.cdc.nbs.entity.odse.DataSource;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportDataSource(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean hasJurisdictionSecurity,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean hasFacilitySecurity) {

  public ReportDataSource(DataSource entity) {
    this(
        entity.getId(),
        entity.getDataSourceName(),
        "Y".equals(entity.getJurisdictionSecurity().toString()),
        "Y".equals(entity.getReportingFacilitySecurity().toString()));
  }
}
