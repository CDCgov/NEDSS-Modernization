package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.question.WaQuestionHist;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionHistRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@Component
@Transactional
public class QuestionUpdater {

    private final WaQuestionHistRepository histRepository;
    private final WaQuestionRepository repository;
    private final QuestionMapper questionMapper;

    public QuestionUpdater(
            WaQuestionHistRepository histRepository,
            WaQuestionRepository repository,
            QuestionMapper questionMapper) {
        this.histRepository = histRepository;
        this.repository = repository;
        this.questionMapper = questionMapper;
    }

    /**
     * Sets the status of the specified question to either 'Active' or 'Inactive'.
     * 
     * Returns the updated question
     * 
     * @param userId
     * @param questionId
     * @param active
     * @return
     */
    public Question setStatus(final Long userId, final Long questionId, final boolean active) {
        return repository.findById(questionId).map(q -> {
            createHistoryEvent(q);
            q.statusChange(asSetStatus(userId, questionId, active));
            return questionMapper.toQuestion(repository.save(q));
        }).orElseThrow(() -> new QuestionNotFoundException("Failed to find question with id: " + questionId));
    }

    private QuestionCommand.SetStatus asSetStatus(long userId, long questionId, boolean active) {
        return new QuestionCommand.SetStatus(active, userId, Instant.now());
    }

    private void createHistoryEvent(WaQuestion question) {
        WaQuestionHist history = new WaQuestionHist(question);
        histRepository.save(history);
    }

}
