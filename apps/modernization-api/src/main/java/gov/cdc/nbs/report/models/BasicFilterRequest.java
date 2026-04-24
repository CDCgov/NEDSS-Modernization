package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record BasicFilterRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Long reportFilterUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank List<String> values) {}
