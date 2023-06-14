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
@DiscriminatorValue(TextQuestion.TEXT_QUESION_TYPE)
public class TextQuestion extends WaQuestion {
    static final String TEXT_QUESION_TYPE = "TEXT";

    @Column(name = "mask", length = 50)
    private String mask;

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    @Column(name = "default_value", length = 300)
    private String defaultValue;

    @Override
    public String getDataType() {
        return TextQuestion.TEXT_QUESION_TYPE;
    }

    public TextQuestion(QuestionCommand.AddTextQuestion command) {
        super(command);

        this.defaultValue = command.defaultValue();
        this.mask = command.mask();
        this.fieldSize = command.fieldLength();

        // Audit
        created(command);

        // Reporting
        setReportingData(command.reportingData());

        // Messaging
        setMessagingData(command.messagingData());
    }

}
