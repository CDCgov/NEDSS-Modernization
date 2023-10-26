package gov.cdc.nbs.questionbank.question.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNumericQuestionRequest extends CreateQuestionRequest {
    private NumericMask mask;
    private Integer fieldLength;
    private Long defaultValue;
    private Long minValue;
    private Long maxValue;
    private String relatedUnitsLiteral;
    private Long relatedUnitsValueSet;

    public enum NumericMask {
        NUM_DD,
        NUM_MM,
        NUM_YYYY,
        NUM,
        NUM_EXT,
        NUM_SN,
        NUM_TEMP
    }
}
