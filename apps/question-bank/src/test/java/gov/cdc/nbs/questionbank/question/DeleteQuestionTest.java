package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.kafka.message.DeleteQuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;

 class DeleteQuestionTest {
	
	@Mock
	KafkaTemplate<String, DeleteQuestionRequest> kafkaQuestionDeleteTemplate;

	@InjectMocks
	QuestionHandler questionHandler;
	
	@Mock
	QuestionRepository questionRepository;
	
	@Mock
	QuestionDeleteEventProducer questionDeleteProducer;
	
	@Mock
	RequestStatusProducer statusProducer;
	
	@Mock
	QuestionCreator creator;
	
	

	public DeleteQuestionTest() {
		MockitoAnnotations.openMocks(this);
		questionHandler = new QuestionHandler(questionRepository,questionDeleteProducer,creator, statusProducer);
	}

	@Test
	void processDeleteQuestion() {
		
		Long questionId = UUID.randomUUID().getLeastSignificantBits();
		Long userId = UUID.randomUUID().getLeastSignificantBits();
		
		when(questionRepository.deleteQuestion(questionId, Boolean.FALSE))
		.thenReturn(1);

	    QuestionBankEventResponse result =  questionHandler.processDeleteQuestion(questionId, userId);

		assertEquals(questionId.longValue() ,result.getQuestionId().longValue());
		assertEquals(result.getMessage(), Constants.DELETE_SUCCESS_MESSAGE);

	}
	
	@Test
	void deleteQuestion() {
		Long questionId = UUID.randomUUID().getLeastSignificantBits();
		when(questionRepository.deleteQuestion(questionId, Boolean.FALSE))
				.thenReturn(1);
		int result = questionHandler.deleteQuestion(questionId);
		assertEquals(result, 1);

	}

	@Test
	void deleteNonExistentQuestion() {
		int  result = questionHandler.deleteQuestion(null);
		assertEquals(result, -1);
	}

	public TextQuestionEntity testQuestion(Long questionId) {
		TextQuestionEntity question = new TextQuestionEntity(
				new QuestionCommand.AddTextQuestion(questionId, UUID.randomUUID().getLeastSignificantBits(),
						Instant.now(), "Test", "ToolTip", Integer.valueOf(100), "placeholder", "default"));
		return question;
	}

}
