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
@DiscriminatorValue(DropDownQuestionEntity.TYPE)
public class DropDownQuestionEntity extends DisplayElementEntity {
    static final String TYPE = "dropdown_question";

    @Column(name = "label", length = 300)
    private String label;

    @Column(name = "tooltip", length = 200)
    private String tooltip;

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

    public DropDownQuestionEntity(QuestionCommand.AddDropDownQuestion command) {
        this.label = command.label();
        this.tooltip = command.tooltip();
        this.valueSet = command.valueSet();
        this.defaultAnswer = command.defaultValue();
        this.setAudit(new AuditInfo(command));
        this.setVersion(1);
    }

}
