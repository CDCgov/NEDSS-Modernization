package gov.cdc.nbs.report.models;

import gov.cdc.nbs.report.AdvancedQueryException;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdvancedFilterConfiguration(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long reportFilterUid,
    AdvancedQuery.RuleGroup defaultValue,
    //  The resulting SQL query portion extracted during construction of the RuleGroup.
    //  Included for debugging purposes.
    String query,
    //  If present, indicates the (first) exception thrown that prevented RuleGroup compilation.
    AdvancedQueryException exception) {}
