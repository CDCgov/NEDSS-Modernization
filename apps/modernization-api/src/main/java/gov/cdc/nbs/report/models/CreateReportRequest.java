package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CreateReportRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long dataSourceId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long libraryId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String reportTitle,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String sectionCode,
    String description,
    String ownerId,
    List<CreateReportFilterRequest> reportFilters) {}
