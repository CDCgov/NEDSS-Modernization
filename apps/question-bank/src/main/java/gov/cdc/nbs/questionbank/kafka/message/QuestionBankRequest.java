package gov.cdc.nbs.questionbank.kafka.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(QuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.CreateTextQuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.UpdateTextQuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.CreateDateQuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.CreateDropdownQuestionRequest.class),
        @JsonSubTypes.Type(QuestionRequest.CreateNumericQuestionRequest.class)
})
public interface QuestionBankRequest {

    String requestId();

    long userId();

    default String type() {
        return QuestionBankRequest.class.getSimpleName();
    }

}
