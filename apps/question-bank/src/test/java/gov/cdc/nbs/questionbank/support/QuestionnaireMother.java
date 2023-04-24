package gov.cdc.nbs.questionbank.support;

import java.util.List;
import gov.cdc.nbs.questionbank.entities.Condition;
import gov.cdc.nbs.questionbank.entities.QuestionGroup;
import gov.cdc.nbs.questionbank.entities.Questionnaire;

public class QuestionnaireMother {

    public static Questionnaire questionnaire(
            String type, List<QuestionGroup> questionGroups,
            List<Condition> conditions) {
        var q = new Questionnaire();
        q.setConditions(conditions);
        q.setQuestionnaireType(type);
        q.setQuestionGroups(questionGroups);
        return q;
    }

}
