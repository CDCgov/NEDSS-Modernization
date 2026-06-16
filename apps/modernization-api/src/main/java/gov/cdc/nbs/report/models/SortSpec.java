package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;

public record SortSpec(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long columnUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) SortDirection direction) {}
