package gov.cdc.nbs.questionbank.pagerules.request;

import java.util.Collection;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.pagerules.Rule;
import io.swagger.annotations.ApiModelProperty;

public record SourceQuestionRequest(@ApiModelProperty(required = true) Rule.RuleFunction ruleFunction,
    Collection<PagesQuestion> targetQuestions) {

}
