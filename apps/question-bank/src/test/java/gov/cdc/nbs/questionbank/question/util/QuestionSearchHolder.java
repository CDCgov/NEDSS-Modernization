package gov.cdc.nbs.questionbank.question.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.model.Question;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class QuestionSearchHolder {

    private WaQuestion searchQuestion;
    private Page<Question> questionResults;

}
