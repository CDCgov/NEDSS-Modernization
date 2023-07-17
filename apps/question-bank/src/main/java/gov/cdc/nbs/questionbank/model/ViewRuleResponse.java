package gov.cdc.nbs.questionbank.model;

import java.util.List;

public record ViewRuleResponse(
        Long ruleId,
        Long templateUid,
        String ruleFunction,
        String ruleDescription,
        String sourceIdentifier,
        List<String> sourceValue,
        String comparator,
        String targetType,
        String errorMsgText,
        List<String> targetValueIdentifier) {
    public record SourceValues(List<String> sourceValueId, List<String> sourceValueText) {
    }

}
