package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record BasicFilterRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @Positive Long reportFilterUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull List<String> values) {}
