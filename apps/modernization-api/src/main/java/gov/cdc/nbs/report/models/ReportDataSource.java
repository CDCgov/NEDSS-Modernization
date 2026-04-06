package gov.cdc.nbs.report.models;

import gov.cdc.nbs.entity.odse.DataSource;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportDataSource(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name) {

  public ReportDataSource(DataSource entity) {
    this(entity.getDataSourceName());
  }
}
