package gov.cdc.nbs.questionbank.pagerules.request;

import java.util.List;
import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import io.swagger.annotations.ApiModelProperty;

public record RuleRequest(
    @ApiModelProperty(required = true) RuleFunction ruleFunction,
    String description,
    @ApiModelProperty(required = true) String sourceIdentifier,
    @ApiModelProperty(required = true) boolean anySourceValue,
    List<SourceValue> sourceValues,
    @ApiModelProperty(required = true) Comparator comparator,
    @ApiModelProperty(required = true) TargetType targetType,
    @ApiModelProperty(required = true) List<String> targetIdentifiers,
    String sourceText,
    List<String> targetValueText) {
}
