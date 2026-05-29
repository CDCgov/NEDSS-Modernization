package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveAsReportRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank String reportTitle,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank String sectionCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank ReportConstants.ReportGroup group,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @Valid ReportExecutionRequest executionRequest,
    String description) {}
