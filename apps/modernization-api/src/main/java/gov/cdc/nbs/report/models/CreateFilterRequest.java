package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CreateFilterRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long filterCodeUid,
    Long columnUid,
    List<CreateFilterValueRequest> filterValues) {}
