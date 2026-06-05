package gov.cdc.nbs.report.models;

import gov.cdc.nbs.entity.odse.ReportLibrary;
import io.swagger.v3.oas.annotations.media.Schema;

public record Library(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String description,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isBuiltin,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean allowColumnSelection) {

  public Library(ReportLibrary dbLibrary) {
    this(
        dbLibrary.getId(),
        dbLibrary.getRunner(),
        dbLibrary.getLibraryName(),
        dbLibrary.getDescTxt(),
        dbLibrary.isBuiltin(),
        dbLibrary.allowColumnSelection());
  }
}
