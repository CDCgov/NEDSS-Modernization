package gov.cdc.nbs.report;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;

public record ReportConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) HashMap<String, Long> id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner) {}
