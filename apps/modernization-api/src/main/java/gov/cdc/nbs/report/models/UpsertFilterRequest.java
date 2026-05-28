package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpsertFilterRequest(
        Long id, // Update if present, create if not
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        Long filterCodeUid, //  Pertains to FilterCode.ID
    Long columnUid,
    ReportConstants.SelectType selectType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isRequired) {}
