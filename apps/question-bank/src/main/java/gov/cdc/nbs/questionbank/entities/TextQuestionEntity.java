package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@DiscriminatorValue(TextQuestionEntity.TYPE)
public class TextQuestionEntity extends DisplayElementEntity {
    static final String TYPE = "text_question";

    @Column(name = "label", length = 300)
    private String label;

    @Column(name = "tooltip", length = 200)
    private String tooltip;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "placeholder", length = 100)
    private String placeholder;

    @Column(name = "default_text_value")
    private String defaultTextValue;

    @Override
    public String getDisplayType() {
        return TYPE;
    }

    public TextQuestionEntity(QuestionCommand.AddTextQuestion command) {
        this.label = command.label();
        this.tooltip = command.tooltip();
        this.maxLength = command.maxLength();
        this.placeholder = command.placeholder();
        this.defaultTextValue = command.defaultValue();
        this.setAudit(new AuditInfo(command));
        this.setVersion(1);
    }

}
