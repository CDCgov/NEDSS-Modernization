package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportResult(
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"table"})
        @JsonProperty("content_type")
        String contentType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String content,
    String header,
    String subheader,
    String description) {}
