package gov.cdc.nbs.questionbank.question;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.RequestStatus;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;

public class DeleteQuestionTest {

	@Autowired
	RequestStatusProducer requestStatusProducer;

	RequestProperties requestProperties;

	@Mock
	KafkaTemplate<String, RequestStatus> kafkaQuestionStatusEventTemplate;

	@Mock
	QuestionHandler questionHandler;

	public DeleteQuestionTest() {
		MockitoAnnotations.openMocks(this);
		requestProperties = new RequestProperties(UUID.randomUUID().toString(), "questionbank-status");
		requestStatusProducer = new RequestStatusProducer(kafkaQuestionStatusEventTemplate, requestProperties);
	}

	@Test
	public void sendDeleteQuestionResponseStatus() {

		String requestId = UUID.randomUUID().toString();
		Long questionId = UUID.randomUUID().getLeastSignificantBits();
		Long userId = UUID.randomUUID().getLeastSignificantBits();

		var deleteEvent = new QuestionRequest.DeleteQuestionRequest(requestId, questionId, userId);

		ArgumentCaptor<QuestionRequest.DeleteQuestionRequest> captor = ArgumentCaptor
				.forClass(QuestionRequest.DeleteQuestionRequest.class);

		questionHandler.sendDeleteQuestionResponseStatus(deleteEvent);

		verify(questionHandler, times(1)).sendDeleteQuestionResponseStatus(captor.capture());

		QuestionRequest.DeleteQuestionRequest actualRecord = captor.getValue();
		assertEquals(requestId, actualRecord.requestId());
		assertTrue(userId.equals(actualRecord.userId()));
		assertTrue(questionId.equals(actualRecord.questionId()));

	}

}
