package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AdvancedFilterRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Long reportFilterUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @Valid Expr logic) {}
