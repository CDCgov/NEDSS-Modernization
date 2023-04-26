package gov.cdc.nbs.questionbank.entities;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class DropDownQuestionTest {

    @Test
    void should_return_dropdown() {
        var question = new DropDownQuestion();
        assertEquals(Question.QuestionType.DROP_DOWN, question.getQuestionType());
    }
}
