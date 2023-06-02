package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.QuestionDeletedEventProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;

class DeleteQuestionTest {

    @Mock
    QuestionDeletedEventProducer.EnabledProducer producer;

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    DeleteQuestionService deleteQuestionService;

    @Mock
    QuestionCreator creator;



    public DeleteQuestionTest() {
        MockitoAnnotations.openMocks(this);
        deleteQuestionService = new DeleteQuestionService(producer, questionRepository);
    }

    @Test
    void processDeleteQuestion() {

        UUID questionId = UUID.randomUUID();
        Long userId = UUID.randomUUID().getLeastSignificantBits();

        when(questionRepository.deleteQuestion(questionId))
                .thenReturn(1);

        QuestionBankEventResponse result = deleteQuestionService.processDeleteQuestion(questionId, userId);

        assertEquals(questionId, result.getQuestionId());
        assertEquals(Constants.DELETE_SUCCESS_MESSAGE, result.getMessage());
        verify(producer).send(Mockito.any());
    }

    @Test
        
    void deleteQuestion() {
        UUID questionId = UUID.randomUUID();
        when(questionRepository.deleteQuestion(questionId))
                .thenReturn(1);
        int result = deleteQuestionService.deleteQuestion(questionId);
        assertEquals(1, result);
    }

    @Test
    void deleteNonExistentQuestion() {
        int result = deleteQuestionService.deleteQuestion(null);
        assertEquals(result, -1);
    }

    public TextQuestionEntity testQuestion(Long questionId) {
        TextQuestionEntity question = new TextQuestionEntity(
                new QuestionCommand.AddTextQuestion(questionId, UUID.randomUUID().getLeastSignificantBits(),
                        Instant.now(), "Test", "ToolTip", Integer.valueOf(100), "placeholder", "default", null));
        return question;
    }

}
