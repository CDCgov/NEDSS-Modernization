package gov.cdc.nbs.questionbank.pagerules;

public record RuleData(
        String targetIdentifiers,
        String ruleExpression,
        String errorMsgText,
        String sourceIdentifier,
        String sourceText,
        String sourceValues,
        JSFunctionNameHelper jsFunctionNameHelper) {
}


