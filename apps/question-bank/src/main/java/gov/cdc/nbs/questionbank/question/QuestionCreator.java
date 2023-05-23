package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.TextQuestionData;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddTextQuestion;
import gov.cdc.nbs.questionbank.question.repository.DisplayElementRepository;

@Component
class QuestionCreator {

    private final DisplayElementRepository textQuestionRepository;

    public QuestionCreator(DisplayElementRepository textQuestionRepository) {
        this.textQuestionRepository = textQuestionRepository;
    }

    @Transactional
    TextQuestionEntity create(QuestionRequest.TextQuestionData data, long userId) {
        TextQuestionEntity entity = new TextQuestionEntity(asAdd(userId, data));
        return textQuestionRepository.save(entity);
    }

    private AddTextQuestion asAdd(long userId, TextQuestionData data) {
        Instant now = Instant.now();
        return new AddTextQuestion(
                null,
                userId,
                now,
                data.label(),
                data.tooltip(),
                data.maxLength(),
                data.placeholder());
    }

}
