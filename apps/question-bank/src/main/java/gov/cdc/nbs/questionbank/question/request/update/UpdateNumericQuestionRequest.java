package gov.cdc.nbs.questionbank.question.request.update;

import gov.cdc.nbs.questionbank.question.request.create.NumericMask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNumericQuestionRequest extends UpdateQuestionRequest {
  private NumericMask mask;
  private Integer fieldLength;
  private Long defaultValue;
  private Long minValue;
  private Long maxValue;
  private String relatedUnitsLiteral;
  private Long relatedUnitsValueSet;
}
