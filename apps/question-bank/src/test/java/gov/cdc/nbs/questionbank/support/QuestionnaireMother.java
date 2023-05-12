package gov.cdc.nbs.questionbank.support;

import java.util.List;
import gov.cdc.nbs.questionbank.entities.ElementReference;
import gov.cdc.nbs.questionbank.entities.QuestionnaireEntity;

public class QuestionnaireMother {

    public static QuestionnaireEntity questionnaire(
            String type, List<ElementReference> elements,
            List<String> conditions) {
        var q = new QuestionnaireEntity();
        q.setConditionCodes(conditions);
        q.setQuestionnaireType(type);
        q.setElements(elements);
        return q;
    }

}
