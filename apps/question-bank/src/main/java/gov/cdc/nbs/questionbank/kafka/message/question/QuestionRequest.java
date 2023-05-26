package gov.cdc.nbs.questionbank.kafka.message.question;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(QuestionRequest.CreateTextQuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.UpdateTextQuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.CreateDateQuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.CreateDropdownQuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.CreateNumericQuestionRequest.class)})
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
            String placeholder,
            String defaultValue) {
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

    record CreateDropdownQuestionRequest(
            String requestId,
            long userId,
            DropdownQuestionData data) implements QuestionRequest {
    }

    record DropdownQuestionData(
            String label,
            String tooltip,
            UUID valueSet,
            UUID defaultValue,
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
            Integer defaultValue,
            UUID unitValueSet) {
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
