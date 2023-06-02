package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(NumericQuestionEntity.TYPE)
public class NumericQuestionEntity extends QuestionEntity {
    static final String TYPE = "numeric_question";

    @Column(name = "min_value")
    private Integer minValue;

    @Column(name = "max_value")
    private Integer maxValue;

    @Column(name = "default_numeric_value")
    private Integer defaultNumericValue;

    @ManyToOne
    @JoinColumn(name = "units_set")
    private ValueSet unitsSet;

    @Override
    public String getDisplayType() {
        return TYPE;
    }

    public NumericQuestionEntity(QuestionCommand.AddNumericQuestion command) {
        this.setLabel(command.label());
        this.setTooltip(command.tooltip());
        this.minValue = command.minValue();
        this.maxValue = command.maxValue();
        this.defaultNumericValue = command.defaultValue();
        this.unitsSet = command.unitsOptions();
        this.setCodeSet(command.codeSet());
        this.setAudit(new AuditInfo(command));
        this.setVersion(1);
    }

}
