package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(Question.QuestionType.TEXT)
public class TextQuestion extends Question {

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "placeholder", length = 100)
    private String placeholder;

    @Override
    public String getQuestionType() {
        return Question.QuestionType.TEXT;
    }

}
