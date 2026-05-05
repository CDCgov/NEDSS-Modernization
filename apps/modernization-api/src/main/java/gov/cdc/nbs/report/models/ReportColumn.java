package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ReportColumn(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    Integer maxLength,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String title,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String sourceTypeCode,
    String descTxt,
    Character displayable,
    Character filterable,
    Character statusCd,
    LocalDateTime statusTime) {}
