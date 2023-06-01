package gov.cdc.nbs.questionbank.questionnaire;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;

@Component
public class QuestionnaireFinder {
    private final QuestionnaireRepository repository;
    private final EntityMapper entityMapper;

    public QuestionnaireFinder(QuestionnaireRepository repository, EntityMapper entityMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
    }

    @Transactional
    public Questionnaire find(QuestionnaireContext context) {
        return repository.findOneByConditionCodes(context.conditionCd())
                .map(entityMapper::toQuestionnaire)
                .orElse(null);
    }

}
