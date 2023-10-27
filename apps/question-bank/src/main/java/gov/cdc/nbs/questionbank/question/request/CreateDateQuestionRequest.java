package gov.cdc.nbs.questionbank.question.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDateQuestionRequest extends CreateQuestionRequest {
    private DateMask mask;
    private boolean allowFutureDates;

    public enum DateMask {
        DATE
    }
}
