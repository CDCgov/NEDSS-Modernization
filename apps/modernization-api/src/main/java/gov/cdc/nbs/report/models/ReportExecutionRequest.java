package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ReportExecutionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long reportUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long dataSourceUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean isExport,
    List<Long> columnUids,
    List<Filter> filters) {}
