package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ReportColumn(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    Integer columnMaxLength,
    String columnName,
    String columnTitle,
    String columnSourceTypeCode,
    String descTxt,
    Character displayable,
    Character filterable,
    Character statusCd,
    LocalDateTime statusTime) {}
