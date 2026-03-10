package gov.cdc.nbs.questionbank.valueset.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ValueSetSearchRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String query) {}
