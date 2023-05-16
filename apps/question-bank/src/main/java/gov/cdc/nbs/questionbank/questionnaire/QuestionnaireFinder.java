package gov.cdc.nbs.questionbank.questionnaire;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;

@Component
public class QuestionnaireFinder {
    private final QuestionnaireRepository repository;
    private final QuestionnaireMapper questionnaireMapper;

    public QuestionnaireFinder(QuestionnaireRepository repository) {
        this.repository = repository;
        this.questionnaireMapper = new QuestionnaireMapper();
    }

    @Transactional
    public Questionnaire find(QuestionnaireContext context) {
        return repository.findOneByConditionCodes(context.conditionCd())
                .map(questionnaireMapper::toQuestionnaire)
                .orElse(null);
    }

}
