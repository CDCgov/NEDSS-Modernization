package gov.cdc.nbs.questionbank.pagerules.response;


public record RuleResponse(
    long ruleId,
    long templateId,
    String function,
    String description,
    String sourceQuestion,
    String ruleExpression,
    String sourceValues,
    String comparator,
    String targetType,
    String targetQuestions,
    long totalCount) {
}

