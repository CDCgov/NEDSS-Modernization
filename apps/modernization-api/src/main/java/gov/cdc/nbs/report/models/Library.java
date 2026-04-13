package gov.cdc.nbs.report.models;

import gov.cdc.nbs.entity.odse.ReportLibrary;
import io.swagger.v3.oas.annotations.media.Schema;

public record Library(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String libraryName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isBuiltin) {

  public Library(ReportLibrary dbLibrary) {
    this(
        dbLibrary.getRunner(),
        dbLibrary.getLibraryName(),
        dbLibrary.getIsBuiltinIndex().toString().equalsIgnoreCase("Y"));
  }
}
