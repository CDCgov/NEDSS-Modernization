package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdvancedQueryResult(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String query,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean hasLabResultVal) {}
