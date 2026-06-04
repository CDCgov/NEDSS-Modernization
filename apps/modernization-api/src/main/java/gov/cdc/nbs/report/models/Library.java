package gov.cdc.nbs.report.models;

import gov.cdc.nbs.entity.odse.ReportLibrary;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

public record Library(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String libraryName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isBuiltin,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean allowColumnSelection,
    @Schema(description = "Optional JSON parameters for the report") Map<String, Object> reportParams) {

  public Library(ReportLibrary dbLibrary) {
    this(
        dbLibrary.getRunner(),
        dbLibrary.getLibraryName(),
        dbLibrary.isBuiltin(),
        dbLibrary.allowColumnSelection(),
        JsonUtils.parseJsonMap(dbLibrary.getReportParams())   // String -> Map
  }
}