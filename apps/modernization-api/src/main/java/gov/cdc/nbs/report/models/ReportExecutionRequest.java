package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ReportExecutionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull long reportUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull long dataSourceUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull boolean isExport,
    List<Long> columnUids,
    @Valid List<Filter> filters) {}
