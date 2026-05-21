package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateReportRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long dataSourceId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long libraryId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String reportTitle,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String sectionCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long ownerId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportConstants.ReportGroup group,
    String description,
    CreateFilterRequest filterRequest) {}
