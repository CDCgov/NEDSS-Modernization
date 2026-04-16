package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record FilterType(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    String codeTable,
    String descTxt,
    String code,
    String codeSetName,
    String type,
    String name) {}
