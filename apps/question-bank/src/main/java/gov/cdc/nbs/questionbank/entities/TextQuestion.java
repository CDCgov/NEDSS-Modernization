package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(TextQuestion.type)
public class TextQuestion extends DisplayElement {
    static final String type = "text_question";

    @Column(name = "label", length = 300)
    private String label;

    @Column(name = "tooltip", length = 200)
    private String tooltip;

    @Column(name = "required")
    private boolean required;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "placeholder", length = 100)
    private String placeholder;

    @Override
    public String getDisplayType() {
        return type;
    }

}
