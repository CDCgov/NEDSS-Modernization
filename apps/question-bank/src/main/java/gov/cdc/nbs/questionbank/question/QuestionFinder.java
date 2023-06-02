package gov.cdc.nbs.questionbank.question;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.QuestionEntity;
import gov.cdc.nbs.questionbank.question.repository.QuestionRepository;
import gov.cdc.nbs.questionbank.questionnaire.EntityMapper;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;

@Component
public class QuestionFinder {

    private final QuestionRepository repository;
    private final EntityMapper entityMapper;

    public QuestionFinder(final QuestionRepository repository, final EntityMapper entityMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
    }

    public Page<Questionnaire.Question> find(QuestionFilter filter, Pageable pageable) {
        return Stream.of(repository.findAllByLabelContaining(filter.searchText(), pageable))
                .map(this::toQuestionPage)
                .findFirst()
                .orElseThrow();
    }

    private Page<Questionnaire.Question> toQuestionPage(Page<QuestionEntity> entityPage) {
        List<Questionnaire.Question> questions = entityPage.get()
                .map(entityMapper::toQuestion)
                .toList();
        return new PageImpl<>(questions, entityPage.getPageable(), entityPage.getTotalElements());
    }

}
