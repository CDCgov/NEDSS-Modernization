package gov.cdc.nbs.questionbank.kafka.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(QuestionRequest.class)
})
public interface QuestionBankRequest {

    String requestId();

    long userId();    

    default String type() {
        return QuestionBankRequest.class.getSimpleName();
    }

}
