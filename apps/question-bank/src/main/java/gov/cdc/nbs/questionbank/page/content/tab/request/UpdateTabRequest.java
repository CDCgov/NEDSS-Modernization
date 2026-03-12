package gov.cdc.nbs.questionbank.page.content.tab.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateTabRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean visible) {}
