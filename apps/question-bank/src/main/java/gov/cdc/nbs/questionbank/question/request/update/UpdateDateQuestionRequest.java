package gov.cdc.nbs.questionbank.question.request.update;

import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest.DateMask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDateQuestionRequest extends UpdateQuestionRequest {
  private DateMask mask;
  private boolean allowFutureDates;
}
