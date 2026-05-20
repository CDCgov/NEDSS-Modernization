package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ReportColumn(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    Integer maxLength,
    // While these aren't required in the db schema, they are required in the UI and functionally
    // necessary for the application to function, so making them required on the API side
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String title,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String sourceTypeCode,
    String descTxt,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isDisplayable,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isFilterable,
    String codeDescCd,
    String codesetNm,
    Character statusCd,
    LocalDateTime statusTime) {}
