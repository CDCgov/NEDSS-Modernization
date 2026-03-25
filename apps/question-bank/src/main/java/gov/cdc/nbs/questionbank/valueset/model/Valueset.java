package gov.cdc.nbs.questionbank.valueset.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record Valueset(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String type,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String code,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    String description) {}
