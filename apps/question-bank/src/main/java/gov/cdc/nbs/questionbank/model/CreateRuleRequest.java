package gov.cdc.nbs.questionbank.model;

import java.util.List;

public record CreateRuleRequest(
        String ruleFunction,
        String ruleDescription,
        String sourceText,
        String sourceIdentifier,
        List<SourceValues> sourceValue,

        boolean anySourceValue,
        String comparator,
        String targetType,
        List<String> targetValueText,

        List<String> targetValueIdentifier) {
    public record SourceValues(List<String> sourceValueId, List<String> sourceValueText) {
    }
}
