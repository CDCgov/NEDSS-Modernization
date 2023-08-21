package gov.cdc.nbs.questionbank.entity.question;

import static gov.cdc.nbs.questionbank.util.Util.requireNonNull;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue(DateQuestionEntity.DATE_QUESION_TYPE)
public class DateQuestionEntity extends WaQuestion {
    static final String DATE_QUESION_TYPE = "DATE";

    @Column(name = "mask", length = 50)
    private String mask;

    public void setMask(String mask) {
        this.mask = requireNonNull(mask, "Mask");
    }

    public void setFutureDateIndCd(boolean allowFutureDates) {
        this.setFutureDateIndCd(allowFutureDates ? 'T' : 'F');
    }

    @Override
    public String getDataType() {
        return DateQuestionEntity.DATE_QUESION_TYPE;
    }

    public DateQuestionEntity(QuestionCommand.AddDateQuestion command) {
        super(command);

        setMask(command.mask());
        setFutureDateIndCd(command.allowFutureDates());

        // Audit
        created(command);

        // Reporting
        setReportingData(command);

        // Messaging
        setMessagingData(command.messagingData());
    }

    @Override
    public void update(QuestionCommand.Update command) {
        // Update general fields
        update(command.questionData());

        // Date fields
        setMask(command.mask());
        setFutureDateIndCd(command.allowFutureDates());

        // Reporting
        setReportingData(command);

        // Messaging
        setMessagingData(command.messagingData());

        // Audit
        changed(command);
    }

}
