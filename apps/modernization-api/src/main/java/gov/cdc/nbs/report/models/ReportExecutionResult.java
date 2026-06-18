package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/** Model returned to the user/UI when they execute a report */
public record ReportExecutionResult(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull LibraryExecutionResult result,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String query,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull LocalDateTime timestamp) {}
