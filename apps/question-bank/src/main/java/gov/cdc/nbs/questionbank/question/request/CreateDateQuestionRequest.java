package gov.cdc.nbs.questionbank.question.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDateQuestionRequest extends CreateQuestionRequest {
    private String mask;
    private boolean allowFutureDates;
}
