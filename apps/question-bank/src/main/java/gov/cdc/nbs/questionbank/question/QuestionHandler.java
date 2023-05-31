package gov.cdc.nbs.questionbank.question;
import java.util.UUID;


import org.springframework.stereotype.Service;

import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.exception.DeleteException;
import gov.cdc.nbs.questionbank.kafka.exception.RequestException;
import gov.cdc.nbs.questionbank.kafka.exception.UserNotAuthorizedException;
import gov.cdc.nbs.questionbank.kafka.message.DeleteQuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionHandler {
	
	private final QuestionRepository questionRepository;
    private final QuestionDeleteEventProducer questionDeleteProducer;
    private final QuestionCreator creator;
    private final RequestStatusProducer statusProducer;
    private  UserService userService;


    public void handleQuestionRequest(QuestionRequest request) {
        if (userService.isAuthorized(request.userId(), "LDFADMINISTRATION-SYSTEM")) {
            createQuestion(request);
        } else {
            notAuthorized(request.requestId());
        }
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
    
	public QuestionBankEventResponse processDeleteQuestion(Long questionId, Long userId) {
		// delete question send response to user
		deleteQuestion(questionId);
		// send delete event info to topic
		DeleteQuestionRequest event = DeleteQuestionRequest.builder().questionId(questionId).userId(userId)
				.requestId(getRequestId()).build();
		sendQuestionDeleteEvent(event);
		return  QuestionBankEventResponse.builder().questionId(questionId)
				.message(Constants.DELETE_SUCCESS_MESSAGE).build();
	}

	public int  deleteQuestion(Long questionId) {
		int  updated = -1;
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

	private void sendQuestionDeleteEvent(DeleteQuestionRequest status) {
		questionDeleteProducer.send(status);
	}

	private String getRequestId() {
		return String.format(Constants.APP_ID + "_%s", UUID.randomUUID());
	}
	
}
