package gov.cdc.nbs.questionbank.pagerules.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleResponse{
    String ruleFunction;
    String ruleDescription;
    String source;
    String sourceValues;
    String targetType;
    String errorMsgText;
    String comparator;
    String targetValue;
}

