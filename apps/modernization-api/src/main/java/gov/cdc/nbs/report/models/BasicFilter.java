package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record BasicFilter(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<String> values) {}
