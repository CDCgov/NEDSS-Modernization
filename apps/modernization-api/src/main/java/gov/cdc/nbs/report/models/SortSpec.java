package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.ReportConstants.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public record SortSpec(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long columnUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) SortDirection direction) {

    /**
     * Converts the record into a generic map representation for external API pipelines.
     */
    public Map<String, Object> toMap() {
        // Note: Map.of will throw a NullPointerException if columnUid or direction is null.
        return Map.of(
                "column_uid", this.columnUid,
                "direction", this.direction.name() // Converts the Enum to its String equivalent ("ASC"/"DESC")
        );
    }
}
