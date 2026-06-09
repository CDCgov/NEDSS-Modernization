package gov.cdc.nbs.report.models;

import gov.cdc.nbs.entity.odse.DataSource;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportDataSource(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name) {
public record ReportDataSource(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Character jurisdictionSecurity,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Character facilitySecurity) {

  public ReportDataSource(DataSource entity) {
    this(entity.getId(), entity.getDataSourceName());
    this(
        entity.getDataSourceName(),
        entity.getJurisdictionSecurity(),
        entity.getReportingFacilitySecurity());
  }
}
