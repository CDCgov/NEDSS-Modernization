package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdvancedFilterRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Expr logic) {}
