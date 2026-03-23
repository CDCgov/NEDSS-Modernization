package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public record ReportConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long reportUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long dataSourceUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String runner) {

    public boolean isPython() {
        return Objects.equals(runner(), "python");
    }
}
