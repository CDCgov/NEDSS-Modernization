package gov.cdc.nbs.questionbank.question.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCodedQuestionRequest extends CreateQuestionRequest {
    private Long valueSet;
    private String defaultValue;
}
