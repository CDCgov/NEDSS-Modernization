package gov.cdc.nbs.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;

public record ReportConfigurationResponse(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) HashMap<String, Long> id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner) {}
