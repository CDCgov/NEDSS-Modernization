package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record FilterOption(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    String codeTable,
    String descTxt,
    String code,
    String filterCodeSetName,
    String filterType,
    String filterName) {}
