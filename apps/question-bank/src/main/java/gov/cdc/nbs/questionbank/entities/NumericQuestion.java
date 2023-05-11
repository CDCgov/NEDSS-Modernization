package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(Question.QuestionType.NUMERIC)
public class NumericQuestion extends Question {

    @Column(name = "min_value")
    private Integer minValue;

    @Column(name = "max_value")
    private Integer maxValue;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "units_set"), @JoinColumn(name = "version")})
    private ValueSet unitsSet;

    @Override
    public String getQuestionType() {
        return Question.QuestionType.NUMERIC;
    }

}
