package gov.cdc.nbs.questionbank.condition.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record Condition(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String programArea,
    String conditionFamily,
    String coinfectionGroup,
    Character nndInd,
    String page,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Character status) {}
