package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(Question.QuestionType.DATE)
public class DateQuestion extends Question {

    @Column(name = "allow_future_dates", nullable = false)
    private boolean allowFuture;

    @Override
    public String getQuestionType() {
        return Question.QuestionType.DATE;
    }
}
