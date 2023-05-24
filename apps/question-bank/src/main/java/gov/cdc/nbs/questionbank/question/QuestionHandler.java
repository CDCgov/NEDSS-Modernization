package gov.cdc.nbs.questionbank.question;

import org.springframework.stereotype.Service;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.kafka.exception.RequestException;
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
            notAuthorized("User not authorized to create Question", request.requestId());
        }
    }

    private void createQuestion(QuestionRequest request) {
        DisplayElementEntity entity;
        if (request instanceof QuestionRequest.CreateTextQuestionRequest createTextQuestionRequest) {
            entity = creator.create(createTextQuestionRequest.data(), request.userId());
        } else if (request instanceof QuestionRequest.CreateDateQuestionRequest createDateQuestionRequest) {
            entity = creator.create(createDateQuestionRequest.data(), request.userId());
        } else if (request instanceof QuestionRequest.CreateDropDownQuestionRequest createDropDownRequest) {
            entity = creator.create(createDropDownRequest.data(), request.userId());
        } else if (request instanceof QuestionRequest.CreateNumericQuestionRequest createNumericRequest) {
            entity = creator.create(createNumericRequest.data(), request.userId());
        } else if (request instanceof QuestionRequest.CreateTextElementRequest createTextElementRequest) {
            entity = creator.create(createTextElementRequest.data(), request.userId());
        } else {
            throw new RequestException("Failed to handle question type", request.requestId());
        }

        statusProducer.successful(
                request.requestId(),
                "Successfully created Question",
                entity.getId().toString());
    }

    private void notAuthorized(String message, String requestId) {
        throw new RequestException(message, requestId);
    }
}
