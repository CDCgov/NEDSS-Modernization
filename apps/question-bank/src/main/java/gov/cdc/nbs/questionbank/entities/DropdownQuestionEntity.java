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
@DiscriminatorValue(DropdownQuestionEntity.TYPE)
public final class DropdownQuestionEntity extends QuestionEntity {
    static final String TYPE = "dropdown_question";

    @ManyToOne
    @JoinColumn(name = "value_set_id")
    private ValueSet valueSet;

    @ManyToOne
    @JoinColumn(name = "default_answer_id")
    private ValueEntity defaultAnswer;

    @Column(name = "multiselect")
    private boolean multiSelect;

    @Override
    public String getDisplayType() {
        return TYPE;
    }

    public DropdownQuestionEntity(QuestionCommand.AddDropDownQuestion command) {
        this.setLabel(command.label());
        this.setTooltip(command.tooltip());
        this.valueSet = command.valueSet();
        this.defaultAnswer = command.defaultValue();
        this.multiSelect = command.isMultiSelect();
        this.setCodeSet(command.codeSet());
        this.setAudit(new AuditInfo(command));
        this.setVersion(1);
    }

}
