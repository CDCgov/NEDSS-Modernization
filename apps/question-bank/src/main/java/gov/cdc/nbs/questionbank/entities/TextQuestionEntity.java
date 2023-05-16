package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(TextQuestionEntity.type)
public class TextQuestionEntity extends DisplayElementEntity {
    static final String type = "text_question";

    @Column(name = "label", length = 300)
    private String label;

    @Column(name = "tooltip", length = 200)
    private String tooltip;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "placeholder", length = 100)
    private String placeholder;

    @Override
    public String getDisplayType() {
        return type;
    }

}
