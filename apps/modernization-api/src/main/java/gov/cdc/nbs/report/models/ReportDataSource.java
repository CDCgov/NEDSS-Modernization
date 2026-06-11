package gov.cdc.nbs.report.models;

import gov.cdc.nbs.entity.odse.DataSource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

public record ReportDataSource(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean hasJurisdictionSecurity,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean hasFacilitySecurity) {

  public ReportDataSource(DataSource entity) {
    this(
            Objects.requireNonNull(entity, "DataSource entity cannot be null").getId(),
            entity.getDataSourceName(),
            entity.getJurisdictionSecurity() != null && "Y".equalsIgnoreCase(entity.getJurisdictionSecurity().toString()),
            entity.getReportingFacilitySecurity() != null && "Y".equalsIgnoreCase(entity.getReportingFacilitySecurity().toString())
    );
  }
}
