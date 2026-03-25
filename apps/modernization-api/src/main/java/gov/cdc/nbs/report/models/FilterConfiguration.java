package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record FilterConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long columnUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String columnName,
    String columnTitle,
    String columnType,
    String columnDescription,
    String filterName,
    String filterType,
    String filterCode,
    String filterValues) {}
