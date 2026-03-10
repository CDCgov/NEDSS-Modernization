package gov.cdc.nbs.questionbank.question.util;

import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.response.GetQuestionResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class QuestionSearchHolder {

  private WaQuestion searchQuestion;
  private Page<Question> questionResults;
  private GetQuestionResponse getQuestionResponse;
}
