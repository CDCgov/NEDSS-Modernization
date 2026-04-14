package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportResult(
    @Schema(allowableValues = {"table"}) @JsonProperty("content_type") String contentType,
    String content,
    String header,
    String subheader,
    String description) {}
