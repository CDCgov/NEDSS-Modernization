package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants.SelectType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record BasicFilterConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    Long reportColumnUid,
    List<String> defaultValues,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean defaultIncludeNulls,
    SelectType selectType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isRequired,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) FilterType filterType) {}
