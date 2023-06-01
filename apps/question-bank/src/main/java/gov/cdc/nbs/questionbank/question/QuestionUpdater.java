package gov.cdc.nbs.questionbank.question;


import java.time.Clock;
import java.time.Instant;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.ValueEntity;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.TextQuestionData;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDateQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDropDownQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddNumericQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddTextQuestion;
import gov.cdc.nbs.questionbank.question.repository.DisplayElementRepository;

@Component
public class QuestionUpdater {
    @Transactional
    TextQuestionEntity update(QuestionRequest.UpdateTextQuestionRequest data, long userId) {
        TextQuestionEntity entity = new TextQuestionEntity(asAdd(userId, data));
        return displayElementRepository.save(entity);
    }

}
