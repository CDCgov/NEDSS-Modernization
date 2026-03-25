package gov.cdc.nbs.questionbank.page.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PageValidationRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name) {}
