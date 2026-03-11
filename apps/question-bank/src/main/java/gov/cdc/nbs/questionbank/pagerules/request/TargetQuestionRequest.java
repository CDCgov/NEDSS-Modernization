package gov.cdc.nbs.questionbank.pagerules.request;

import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.pagerules.Rule;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

public record TargetQuestionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Rule.RuleFunction ruleFunction,
    PagesQuestion sourceQuestion,
    Collection<PagesQuestion> targetQuestion) {}
