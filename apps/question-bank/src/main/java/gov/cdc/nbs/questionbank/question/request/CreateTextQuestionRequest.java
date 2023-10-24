package gov.cdc.nbs.questionbank.question.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTextQuestionRequest extends CreateQuestionRequest {
    private String mask;
    private Integer fieldLength;
    private String defaultValue;
}
