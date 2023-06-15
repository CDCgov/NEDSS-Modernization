package gov.cdc.nbs.questionbank.entity.question;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(DateQuestionEntity.DATE_QUESION_TYPE)
public class DateQuestionEntity extends WaQuestion {
    static final String DATE_QUESION_TYPE = "DATE";

    @Column(name = "mask", length = 50)
    private String mask;

    @Column(name = "future_date_ind_cd")
    private Character futureDateIndCd;

    @Override
    public String getDataType() {
        return DateQuestionEntity.DATE_QUESION_TYPE;
    }

    public DateQuestionEntity(QuestionCommand.AddDateQuestion command) {
        super(command);

        this.mask = command.mask();
        this.futureDateIndCd = command.allowFutureDates() ? 'T' : 'F';

        // Audit
        created(command);

        // Reporting
        setReportingData(command.reportingData());

        // Messaging
        setMessagingData(command.messagingData());
    }

}
