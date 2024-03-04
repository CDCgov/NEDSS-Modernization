package gov.cdc.nbs.questionbank.question.request.update;

import gov.cdc.nbs.questionbank.question.request.create.TextMask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTextQuestionRequest extends UpdateQuestionRequest {
  private TextMask mask;
  private Integer fieldLength;
  private String defaultValue;
}
