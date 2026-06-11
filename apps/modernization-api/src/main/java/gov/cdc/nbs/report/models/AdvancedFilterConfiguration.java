package gov.cdc.nbs.report.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdvancedFilterConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    AdvancedQuery.RuleGroup defaultValue,
    //  The resulting SQL query portion extracted during construction of the RuleGroup.
    //  Included for debugging purposes if the RuleGroup cannot be constructed.
    String query
    ) {
    public AdvancedFilterConfiguration {
        if (defaultValue == null && (query == null || query.isBlank())) {
            throw new IllegalArgumentException("Either 'defaultValue' or 'query' must be provided");
        }
    }
}
