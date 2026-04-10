package gov.cdc.nbs.report.models;

import gov.cdc.nbs.entity.odse.ReportLibrary;
import io.swagger.v3.oas.annotations.media.Schema;

public record Library(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String libraryName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isBuiltin,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Integer version) {

  public Library(ReportLibrary entity) {
    this(
        entity.getRunner(),
        entity.getLibraryName(),
        entity.getIsBuiltinIndex().toString().equalsIgnoreCase("Y"),
        entity.getVersion());
  }
}
