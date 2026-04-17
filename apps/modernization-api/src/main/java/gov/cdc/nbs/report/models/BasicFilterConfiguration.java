package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record BasicFilterConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    Long reportColumnUid,
    List<String> defaultValue,
    Integer minValueCount,
    Integer maxValueCount,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) FilterType filterType) {}
