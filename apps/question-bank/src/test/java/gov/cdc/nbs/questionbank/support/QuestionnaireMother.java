package gov.cdc.nbs.questionbank.support;

import java.util.List;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.QuestionnaireEntity;
import gov.cdc.nbs.questionbank.questionnaire.QuestionnaireRepository;

@Component
public class QuestionnaireMother {

    private final QuestionnaireRepository repository;

    public QuestionnaireMother(QuestionnaireRepository repository) {
        this.repository = repository;
    }

    public QuestionnaireEntity questionnaire(
            List<String> conditions) {
        var q = new QuestionnaireEntity();
        q.setVersion(1);
        q.setConditionCodes(conditions);
        return repository.save(q);
    }

    public void clean() {
        repository.deleteAll();
    }


}
