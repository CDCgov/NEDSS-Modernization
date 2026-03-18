package gov.cdc.nbs.questionbank.pagerules.request;

import gov.cdc.nbs.questionbank.pagerules.Rule;
import io.swagger.v3.oas.annotations.media.Schema;

public record SourceQuestionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Rule.RuleFunction ruleFunction) {}
