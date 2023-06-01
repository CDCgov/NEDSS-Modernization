package gov.cdc.nbs.questionbank.question;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.exception.DeleteException;
import gov.cdc.nbs.questionbank.kafka.message.DeleteQuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteQuestionService {

	@Value("${kafkadef.topics.questionbank.deletestatus}")
	private String questionDeleteStatusTopic;

	private final KafkaTemplate<String, DeleteQuestionRequest> kafkaQuestionDeleteTemplate;

	private final QuestionRepository questionRepository;

	public QuestionBankEventResponse processDeleteQuestion(Long questionId, Long userId) {
		// delete question send response to user
		deleteQuestion(questionId);
		// send delete event info to topic
		DeleteQuestionRequest event = DeleteQuestionRequest.builder().questionId(questionId).userId(userId)
				.requestId(getRequestId()).build();
		send(event);
		return QuestionBankEventResponse.builder().questionId(questionId).message(Constants.DELETE_SUCCESS_MESSAGE)
				.build();
	}

	public int deleteQuestion(Long questionId) {
		int updated = -1;
		if (questionId == null)
			return updated;
		try {
			// mark question as inactive
			updated = questionRepository.deleteQuestion(questionId, false);
		} catch (Exception e) {
			throw new DeleteException();
		}

		return updated;

	}

	private void send(final DeleteQuestionRequest status) {
		kafkaQuestionDeleteTemplate.send(questionDeleteStatusTopic, status);
	}

	private String getRequestId() {
		return String.format(Constants.APP_ID + "_%s", UUID.randomUUID());
	}

}
