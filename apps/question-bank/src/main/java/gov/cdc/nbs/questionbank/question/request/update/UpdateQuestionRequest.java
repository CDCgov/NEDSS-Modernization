package gov.cdc.nbs.questionbank.question.request.update;

import gov.cdc.nbs.questionbank.question.request.QuestionRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class UpdateQuestionRequest extends QuestionRequest {
  DataType type; // editable if not in use

  public enum DataType {
    TEXT,
    NUMERIC,
    DATE,
    CODED
  }
}
