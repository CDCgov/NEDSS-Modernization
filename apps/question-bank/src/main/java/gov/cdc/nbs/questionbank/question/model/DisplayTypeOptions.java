package gov.cdc.nbs.questionbank.question.model;

import java.util.List;

public record DisplayTypeOptions(List<DisplayOption> codedQuestionTypes, List<DisplayOption> dateQuestionTypes,
                                 List<DisplayOption> numericQuestionTypes, List<DisplayOption> textQuestionTypes) {

}
