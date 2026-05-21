package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;

public record SaveAsReportRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String reportTitle,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String sectionCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportConstants.ReportGroup group,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        CreateReportRequest.ReportExecutionConfiguration executionRequest,
    String description) {}
