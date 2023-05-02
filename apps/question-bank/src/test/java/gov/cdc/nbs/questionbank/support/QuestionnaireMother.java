package gov.cdc.nbs.questionbank.support;

import java.util.List;
import gov.cdc.nbs.questionbank.entities.QuestionGroup;
import gov.cdc.nbs.questionbank.questionnaire.Questionnaire;

public class QuestionnaireMother {

    public static Questionnaire questionnaire(
            String type, List<QuestionGroup> questionGroups,
            List<String> conditions) {
        var q = new Questionnaire();
        q.setConditionCodes(conditions);
        q.setQuestionnaireType(type);
        q.setQuestionGroups(questionGroups);
        return q;
    }

}
