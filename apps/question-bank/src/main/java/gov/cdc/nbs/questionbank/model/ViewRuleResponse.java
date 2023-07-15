package gov.cdc.nbs.questionbank.model;

import java.util.List;

public sealed interface ViewRuleResponse {
    Long ruleId();
    Long templateUid();
    String ruleFunction();
    String ruleDescription();
    String sourceIdentifier();
    List<String> sourceValue();
    String comparator();
    String targetType();

    String errorMsgText();
    List<String> targetValueIdentifier();


    record ruleResponse(
            Long ruleId,
            Long templateUid,
            String ruleFunction,
            String ruleDescription,
            String sourceIdentifier,
            List<String> sourceValue,
            String comparator,
            String targetType,
            String errorMsgText,
            List<String> targetValueIdentifier
    ) implements ViewRuleResponse{ }
    record sourceValues(List<String> sourceValueId, List<String> sourceValueText){}

}
