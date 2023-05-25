package gov.cdc.nbs.questionbank.question;

import java.util.UUID;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.kafka.exception.RequestException;
import gov.cdc.nbs.questionbank.kafka.exception.UserNotAuthorizedException;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;

@Service
public class QuestionHandler {

    private final QuestionCreator creator;
    private final UserService userService;
    private final RequestStatusProducer statusProducer;

    public QuestionHandler(
            QuestionCreator creator,
            UserService userService,
            RequestStatusProducer statusProducer) {
        this.creator = creator;
        this.userService = userService;
        this.statusProducer = statusProducer;
    }

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
}
