package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(DropDownQuestion.type)
public class DropDownQuestion extends DisplayElement {
    static final String type = "dropdown_question";

    @Column(name = "label", length = 300)
    private String label;

    @Column(name = "tooltip", length = 200)
    private String tooltip;

    @Column(name = "required")
    private boolean required;

    @ManyToOne
    @JoinColumn(name = "value_set_id")
    private ValueSet valueSet;

    @ManyToOne
    @JoinColumn(name = "default_answer_id")
    private Value defaultAnswer;

    @Column(name = "multiselect", nullable = false)
    private boolean multiSelect;

    @Override
    public String getDisplayType() {
        return type;
    }
}
