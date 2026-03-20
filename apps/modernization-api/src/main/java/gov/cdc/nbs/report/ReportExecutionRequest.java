package gov.cdc.nbs.report;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ReportExecutionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long reportUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long dataSourceUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean isExport,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<Long> columnUids,
    List<Filter> filters) {}
