package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/** Model returned from the report-execution service when a library is invoked */
public record LibraryExecutionResult(
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"table"})
        @JsonProperty("content_type")
        String contentType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String content,
    String subheader,
    String description) {}
