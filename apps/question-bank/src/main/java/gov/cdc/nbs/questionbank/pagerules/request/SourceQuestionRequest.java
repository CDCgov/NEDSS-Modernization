package gov.cdc.nbs.questionbank.pagerules.request;

import gov.cdc.nbs.questionbank.pagerules.Rule;
import io.swagger.annotations.ApiModelProperty;

public record SourceQuestionRequest(@ApiModelProperty(required = true) Rule.RuleFunction ruleFunction) {

}
