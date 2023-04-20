package gov.cdc.nbs.questionbank.questionnaire;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entities.Questionnaire;

@Component
public class QuestionnaireFinder {
    private final QuestionnaireRepository repository;

    public QuestionnaireFinder(QuestionnaireRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Questionnaire find(QuestionnaireContext context) {
        return repository.findByConditionIdAndQuestionnaireType(
                context.conditionId(),
                context.questionnaireType())
                .orElse(null);
    }
}
