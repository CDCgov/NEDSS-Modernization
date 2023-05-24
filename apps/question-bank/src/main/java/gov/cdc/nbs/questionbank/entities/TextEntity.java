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
@DiscriminatorValue(TextEntity.TYPE)
public class TextEntity extends DisplayElementEntity {
    static final String TYPE = "text";

    @Column(name = "text")
    private String text;

    @Override
    public String getDisplayType() {
        return TYPE;
    }


    public TextEntity(QuestionCommand.AddTextElement command) {
        this.text = command.text();
        this.setAudit(new AuditInfo(command));
        this.setVersion(1);
    }

}
