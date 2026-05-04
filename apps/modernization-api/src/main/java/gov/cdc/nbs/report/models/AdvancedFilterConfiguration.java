package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdvancedFilterConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    Expr.RuleGroup defaultValue) {}
