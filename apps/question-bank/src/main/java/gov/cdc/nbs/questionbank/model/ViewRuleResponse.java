package gov.cdc.nbs.questionbank.model;

import gov.cdc.nbs.questionbank.pagerules.QuestionInfo;

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
        List<QuestionInfo> targetQuestions) {
    public record SourceValues(List<String> sourceValueId, List<String> sourceValueText) {
    }

}
