package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(Question.QuestionType.DROP_DOWN)
public class DropDownQuestion extends Question {

    @ManyToOne
    @JoinColumn(name = "answer_set_id")
    private AnswerSet answerSet;

    @ManyToOne
    @JoinColumn(name = "default_answer_id")
    private Answer defaultAnswer;

    @Column(name = "multiselect", nullable = false)
    private boolean multiSelect;

    @Override
    public String getQuestionType() {
        return Question.QuestionType.DROP_DOWN;
    }
}
