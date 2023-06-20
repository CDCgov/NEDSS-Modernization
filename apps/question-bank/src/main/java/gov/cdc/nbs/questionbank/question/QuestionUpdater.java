package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@Component
public class QuestionUpdater {

    private final WaQuestionRepository repository;

    public QuestionUpdater(WaQuestionRepository repository) {
        this.repository = repository;
    }

    public void setStatus(final Long userId, final Long questionId, final boolean active) {
        repository.findById(questionId).map(q -> {
            q.statusChange(asSetStatus(userId, questionId, active));
            return repository.save(q);
        }).orElseThrow(() -> new QuestionNotFoundException("Failed to find question with id: " + questionId));
    }

    private QuestionCommand.SetStatus asSetStatus(long userId, long questionId, boolean active) {
        return new QuestionCommand.SetStatus(active, userId, Instant.now());
    }

}
