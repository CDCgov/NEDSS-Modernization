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
@DiscriminatorValue(DateQuestionEntity.TYPE)
public class DateQuestionEntity extends DisplayElementEntity {
    static final String TYPE = "date_question";

    @Column(name = "label", length = 300)
    private String label;

    @Column(name = "tooltip", length = 200)
    private String tooltip;

    @Column(name = "allow_future_dates")
    private boolean allowFuture;

    @Override
    public String getDisplayType() {
        return TYPE;
    }

    public DateQuestionEntity(QuestionCommand.AddDateQuestion command) {
        this.label = command.label();
        this.tooltip = command.tooltip();
        this.allowFuture = command.allowFutureDates();
        this.setCodeSet(command.codeSet());
        this.setAudit(new AuditInfo(command));
        this.setVersion(1);
    }
}
