package gov.cdc.nbs.questionbank.question.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNumericQuestionRequest extends CreateQuestionRequest {
    private String mask;
    private Integer fieldLength;
    private Long defaultValue;
    private Long minValue;
    private Long maxValue;
    private String relatedUnitsLiteral;
    private Long relatedUnitsValueSet;
}
