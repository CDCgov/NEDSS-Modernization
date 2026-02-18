package gov.cdc.nbs.questionbank.question.request.create;

import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CreateQuestionRequest extends QuestionRequest {
  private CodeSet codeSet;
  private String uniqueId;
  private String subgroup;
}
