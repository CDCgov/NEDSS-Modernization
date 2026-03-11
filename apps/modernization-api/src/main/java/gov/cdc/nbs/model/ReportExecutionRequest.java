package gov.cdc.nbs.model;

import io.swagger.v3.oas.annotations.media.Schema;

// TODO: add filters
public record ReportExecutionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long reportUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long dataSourceUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean isExport,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String[] columns) {}
