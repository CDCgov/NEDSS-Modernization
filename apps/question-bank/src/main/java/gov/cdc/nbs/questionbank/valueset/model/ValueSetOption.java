package gov.cdc.nbs.questionbank.valueset.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record ValueSetOption(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String value,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String codeSetNm,
    String description,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String type) {}
