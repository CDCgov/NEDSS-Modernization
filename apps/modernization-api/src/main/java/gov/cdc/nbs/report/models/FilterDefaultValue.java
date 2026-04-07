package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record FilterDefaultValue(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    Integer sequenceNumber,
    String valueType,
    Long columnUid,
    String operator,
    String valueTxt) {}
