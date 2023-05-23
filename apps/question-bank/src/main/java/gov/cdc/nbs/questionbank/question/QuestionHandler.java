package gov.cdc.nbs.questionbank.question;

import org.springframework.stereotype.Service;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
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
        if (request instanceof QuestionRequest.CreateTextQuestionRequest createTextQuestionRequest) {
            createTextQuestion(createTextQuestionRequest);
        }
    }

    private void createTextQuestion(QuestionRequest.CreateTextQuestionRequest request) {
        if (userService.isAuthorized(request.userId(), "LDFADMINISTRATION-SYSTEM")) {
            TextQuestionEntity entity = creator.create(request.data(), request.userId());
            statusProducer.successful(request.requestId(), "Successfully created Text Question",
                    entity.getId());
        } else {
            notAuthorized("User not authorized to create Text Question", request.requestId());
        }
    }

    private void notAuthorized(String message, String requestId) {
        throw new RequestException(message, requestId);
    }
}
