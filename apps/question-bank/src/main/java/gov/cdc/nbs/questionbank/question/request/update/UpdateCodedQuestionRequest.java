package gov.cdc.nbs.questionbank.question.request.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCodedQuestionRequest extends UpdateQuestionRequest {
  private Long valueSet;
  private String defaultValue;
}
