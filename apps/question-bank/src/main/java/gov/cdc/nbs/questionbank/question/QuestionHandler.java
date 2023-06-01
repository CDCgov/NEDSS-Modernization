package gov.cdc.nbs.questionbank.question;

import java.util.UUID;

import gov.cdc.nbs.questionbank.kafka.exception.UpdateException;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
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
    private static QuestionUpdateEventProducer questionUpdateProducer = null;

    public QuestionHandler(
            QuestionCreator creator,
            UserService userService,
            RequestStatusProducer statusProducer, QuestionUpdateEventProducer questionUpdateProducer) {
        this.creator = creator;
        this.userService = userService;
        this.statusProducer = statusProducer;
        this.questionUpdateProducer = questionUpdateProducer;
    }

    public static QuestionBankEventResponse processUpdateQuestion(Long questionId, Long userId) {
        return null;
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

    public QuestionRequest updateQuestion() {
        return updateQuestion();
    }


    public class QuestionBank {
        public static void main(String[] args) {
            // Example usage
            Long questionId = 123L;
            Long userId = 456L;
            String updatedQuestion = "This is the updated question.";

            QuestionBankEventResponse response = processUpdateQuestion(questionId, userId, updatedQuestion);
            System.out.println(response.getMessage());
        }

        public static QuestionBankEventResponse processUpdateQuestion(Long questionId, Long userId, String updatedQuestion) {
            updateQuestion(questionId, updatedQuestion);

            sendQuestionUpdateEvent(null);
            QuestionBankEventResponse response = new QuestionBankEventResponse(Constants.UPDATE_SUCCESS_MESSAGE, questionId);
            return response;
        }

        public static int updateQuestion(Long questionId, String updatedQuestion) {
            int updated = -1;
            if (questionId == null || updatedQuestion == null)
                return updated;
            try {
                updated = QuestionRepository.updateQuestion(questionId, updatedQuestion);
            } catch (Exception e) {
                throw new UpdateException();
            }
            return updated;
        }
    }

    private static void sendQuestionUpdateEvent(QuestionRequest.UpdateTextQuestionRequest status) {
        questionUpdateProducer.send(status);
    }



    /*public QuestionRequest processUpdateQuestion(Long questionId, Long userId) {

        // delete question send response to user
        updateQuestion(questionId);
        // send delete event info to topic
     QuestionRequest.UpdateTextQuestionRequest event = QuestionRequest.UpdateTextQuestionRequest;
        sendQuestionUpdateEvent(event);
        QuestionRequest response = QuestionRequest.UpdateTextQuestionRequest
                .message(Constants.UPDATE_SUCCESS_MESSAGE).build();
        return response;
    }

    public int  updateQuestion(Long questionId) {
        int  updated = -1;
        if (questionId == null)
            return updated;
        try {
            // mark question as inactive
            updated = QuestionRepository.updateQuestion(questionId);
        } catch (Exception e) {
            throw new RequestException("Failed to update. ");
        }

        return updated;

    }
*/
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
