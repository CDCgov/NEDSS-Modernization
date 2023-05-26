package gov.cdc.nbs.questionbank.question;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.RequestStatus;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.KafkaProducer;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import lombok.RequiredArgsConstructor;
import util.SecurityUtil;

@Service
@RequiredArgsConstructor
public class QuestionHandler {
	private final KafkaProducer producer;
	private final QuestionRepository questionRepository;
	private final RequestProperties requestProperties;

	@Autowired
	private KafkaTemplate<String, RequestStatus> kafkaQuestionStatusEventTemplate;

	@Value("${kafkadef.topics.questionbank.status}")
	private String questionBankTopic;

	public void handleQuestionRequest(QuestionRequest request) {
		RequestStatusProducer requestStatusProducer = new RequestStatusProducer(kafkaQuestionStatusEventTemplate,
				requestProperties);
		// delete question aka marke question inactive
		if (request.type().equals(QuestionRequest.DeleteQuestionRequest.class.getSimpleName())) {
			Optional<DisplayElementEntity> updated = null;
			String additionalMessage = null;
			try {
				updated = questionRepository.deleteQuestion(request.questionId(), false);
			} catch (Exception e) {
				additionalMessage = e.getMessage();
			}
			if (updated != null && updated.isPresent()) {
				if (!updated.get().isActive()) {
					requestStatusProducer.successful(request.requestId(), Constants.DELETE_SUCCESS_MESSAGE,
							request.questionId());
				} else {
					requestStatusProducer.failure(request.requestId(),
							Constants.DELETE_FAILURE_MESSAGE + " : " + additionalMessage);
				}
			} else {
				requestStatusProducer.failure(request.requestId(),
						Constants.DELETE_FAILURE_MESSAGE + " : " + additionalMessage);
			}

		} else if (request.type().equals(QuestionRequest.UpdateTextQuestionRequest.class.getSimpleName())) {
			// Handle update request
			Optional<DisplayElementEntity> updated = null;
			String additionalMessage = null;
			try {
				updated = questionRepository.updateQuestion(request.questionId());
			} catch (Exception e) {
				additionalMessage = e.getMessage();
			}
			if (updated != null && updated.isPresent()) {
				requestStatusProducer.successful(request.requestId(), Constants.UPDATE_SUCCESS_MESSAGE,
						request.questionId());
			} else {
				requestStatusProducer.failure(request.requestId(),
						Constants.UPDATE_FAILURE_MESSAGE + " : " + additionalMessage);
			}
		}
	}

	public QuestionBankEventResponse sendDeleteQuestionEvent(Long questionId) {
		var user = SecurityUtil.getUserDetails();
		var deleteEvent = new QuestionRequest.DeleteQuestionRequest(getRequestId(), questionId, user.getId());
		return sendQuestionDeleteEvent(deleteEvent, questionId);
	}

	private QuestionBankEventResponse sendQuestionDeleteEvent(QuestionRequest request, Long questionId) {
		producer.requestEventEnvelope(request);
		return new QuestionBankEventResponse(request.requestId(), questionId);
	}
	public QuestionBankEventResponse sendUpdateQuestionEvent(Long questionId) {
		var user = SecurityUtil.getUserDetails();
		var updateEvent = new QuestionRequest.UpdateTextQuestionRequest(getRequestId(), questionId, user.getId());
		return sendQuestionUpdateEvent(updateEvent, questionId);
	}

	private QuestionBankEventResponse sendQuestionUpdateEvent(QuestionRequest request, Long questionId) {
		producer.requestEventEnvelope(request);
		return new QuestionBankEventResponse(request.requestId(), questionId);
	}

	private String getRequestId() {
		return String.format(Constants.APP_ID + "_%s", UUID.randomUUID());
	}

}
