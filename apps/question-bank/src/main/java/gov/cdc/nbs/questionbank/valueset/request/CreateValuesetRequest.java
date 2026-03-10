package gov.cdc.nbs.questionbank.valueset.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateValuesetRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String type,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String code,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    String description) {}
