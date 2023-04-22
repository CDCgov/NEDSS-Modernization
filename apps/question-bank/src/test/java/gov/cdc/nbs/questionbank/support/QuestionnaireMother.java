package gov.cdc.nbs.questionbank.support;

import java.util.Set;
import gov.cdc.nbs.questionbank.entities.QuestionGroup;
import gov.cdc.nbs.questionbank.entities.Questionnaire;

public class QuestionnaireMother {

    public static Questionnaire questionnaire(long conditionId, String type, Set<QuestionGroup> questionGroups) {
        Questionnaire q = new Questionnaire();
        q.setConditionId(conditionId);
        q.setQuestionnaireType(type);
        q.setQuestionGroups(questionGroups);
        return q;
    }

}
