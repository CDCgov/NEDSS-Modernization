package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateReportFilterRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long filterCodeUid, Long columnUid) {}
