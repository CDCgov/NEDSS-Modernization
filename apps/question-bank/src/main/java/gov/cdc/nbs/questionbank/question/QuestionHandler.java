package gov.cdc.nbs.questionbank.question;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.kafka.exception.UserNotAuthorizedException;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.KafkaProducer;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import lombok.RequiredArgsConstructor;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.questionbank.kafka.exception.RequestException;

@Service
@RequiredArgsConstructor
public class QuestionHandler {
	
	private final KafkaProducer producer;
	private final QuestionRepository questionRepository;
    private final QuestionCreator creator;
    private final RequestStatusProducer statusProducer;
	private  UserService userService;

	@Value("${kafkadef.topics.questionbank.status}")
	private String questionBankTopic;

	public void handleQuestionRequest(QuestionRequest request) {
		if (userService.isAuthorized(request.userId(), "LDFADMINISTRATION-SYSTEM")) {
			// delete question aka marke question inactive
			if (request.type().equals(QuestionRequest.DeleteQuestionRequest.class.getSimpleName())) {
				sendDeleteQuestionResponseStatus(request);
			}
			else {
				createQuestion(request);	
			}
		} else {
			notAuthorized(request.requestId());
		}
	}
	
	public QuestionBankEventResponse sendDeleteQuestionEvent(Long questionId, Long userId) {
		var deleteEvent = new QuestionRequest.DeleteQuestionRequest(getRequestId(), questionId, userId);
		return sendQuestionDeleteEvent(deleteEvent, questionId);
	}
	
	public void sendDeleteQuestionResponseStatus(QuestionRequest request) {
		Optional<DisplayElementEntity> updated = Optional.empty();
		String additionalMessage = null;
		try {
			updated = questionRepository.deleteQuestion(request.questionId(), false);
		} catch (Exception e) {
			additionalMessage = e.getMessage();
		}
		if (updated.isPresent()) {
			if (!updated.get().isActive()) {
				statusProducer.successful(request.requestId(), Constants.DELETE_SUCCESS_MESSAGE,
						String.valueOf(request.questionId()));
			} else {
				statusProducer.failure(request.requestId(),
						Constants.DELETE_FAILURE_MESSAGE + " : " + additionalMessage);
			}
		} else {
			statusProducer.failure(request.requestId(),
					Constants.DELETE_FAILURE_MESSAGE + " : " + additionalMessage);
		}

	}

	private QuestionBankEventResponse sendQuestionDeleteEvent(QuestionRequest request, Long questionId) {
		producer.requestEventEnvelope(request);
		return new QuestionBankEventResponse(request.requestId(), questionId);
	}

	private String getRequestId() {
		return String.format(Constants.APP_ID + "_%s", UUID.randomUUID());
	}
	

    private void createQuestion(QuestionRequest request) {
        DisplayElementEntity entity;
        if (request instanceof QuestionRequest.CreateTextQuestionRequest createTextQuestionRequest) {
            entity = creator.create(createTextQuestionRequest.data(), request.userId());
        } else if (request instanceof QuestionRequest.CreateDateQuestionRequest createDateQuestionRequest) {
            entity = creator.create(createDateQuestionRequest.data(), request.userId());
        } else if (request instanceof QuestionRequest.CreateDropdownQuestionRequest createDropDownRequest) {
            entity = creator.create(createDropDownRequest.data(), request.userId());
        } else if (request instanceof QuestionRequest.CreateNumericQuestionRequest createNumericRequest) {
            entity = creator.create(createNumericRequest.data(), request.userId());
        } else {
            throw new RequestException("Failed to handle question type", request.requestId());
        }
        sendSuccess(request.requestId(), entity.getId());
    }

    private void sendSuccess(String requestId, UUID id) {
        statusProducer.successful(
                requestId,
                "Successfully created Question",
                id.toString());
    }

    private void notAuthorized(String requestId) {
        throw new UserNotAuthorizedException(requestId);
    }
}
