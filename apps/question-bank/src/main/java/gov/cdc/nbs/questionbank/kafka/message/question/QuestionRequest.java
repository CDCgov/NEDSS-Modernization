package gov.cdc.nbs.questionbank.kafka.message.question;

import gov.cdc.nbs.questionbank.kafka.message.QuestionBankRequest;

public sealed interface QuestionRequest extends QuestionBankRequest {

    record CreateTextQuestionRequest(
            String requestId,
            long userId,
            TextQuestionData data) implements QuestionRequest {
    }

    record TextQuestionData(
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder) {
    }

    record UpdateTextQuestionRequest(
            String requestId,
            long questionId,
            long userId,
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder) implements QuestionRequest {
    }

}
