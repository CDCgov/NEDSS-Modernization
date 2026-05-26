package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateFilterRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        Long filterCodeUid, //  Pertains to FilterCode.ID
    Long columnUid,
    ReportConstants.SelectType selectType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Boolean isRequired) {}
