package gov.cdc.nbs.questionbank.pagerules;

import java.util.List;

public record Rule(
    long id,
    long template,
    String function,
    String description,
    QuestionInfo sourceQuestion,
    boolean anySourceValue,
    List<String> sourceValues,
    String comparator,
    String targetType,
    List<QuestionInfo> targets
) {
}



