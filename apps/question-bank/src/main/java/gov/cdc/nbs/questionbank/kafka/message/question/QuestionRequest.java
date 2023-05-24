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

    record CreateDateQuestionRequest(
            String requestId,
            long userId,
            DateQuestionData data) implements QuestionRequest {
    }

    record DateQuestionData(
            String label,
            String tooltip,
            boolean allowFutureDates) {
    }

    record CreateDropDownQuestionRequest(
            String requestId,
            long userId,
            DropDownQuestionData data) implements QuestionRequest {
    }

    record DropDownQuestionData(
            String label,
            String tooltip,
            String valueSet,
            String defaultValue,
            boolean isMultiSelect) {
    }

    record CreateNumericQuestionRequest(
            String requestId,
            long userId,
            NumericQuestionData data) implements QuestionRequest {
    }

    record NumericQuestionData(
            String label,
            String tooltip,
            Integer minValue,
            Integer maxValue,
            String unitValueSet) {
    }

    record CreateTextElementRequest(
            String requestId,
            long userId,
            TextData data) implements QuestionRequest {
    }

    record TextData(String text) {
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
