package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record FilterConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) DataSourceColumn dataSourceColumn,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) FilterCode filterCode,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<FilterValue> filterValues) {}
