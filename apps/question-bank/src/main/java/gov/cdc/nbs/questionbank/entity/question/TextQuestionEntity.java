package gov.cdc.nbs.questionbank.entity.question;

import static gov.cdc.nbs.questionbank.question.util.QuestionUtil.requireNonNull;
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
@DiscriminatorValue(TextQuestionEntity.TEXT_QUESION_TYPE)
public class TextQuestionEntity extends WaQuestion {
    static final String TEXT_QUESION_TYPE = "TEXT";

    @Column(name = "mask", length = 50)
    private String mask;

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    @Column(name = "default_value", length = 300)
    private String defaultValue;

    @Override
    public String getDataType() {
        return TextQuestionEntity.TEXT_QUESION_TYPE;
    }

    public TextQuestionEntity(QuestionCommand.AddTextQuestion command) {
        super(command);

        this.defaultValue = command.defaultValue();
        this.mask = requireNonNull(command.mask(), "Mask must not be null");
        this.fieldSize = requireNonNull(command.fieldLength(), "Field length must not be null");

        // Audit
        created(command);

        // Reporting
        setReportingData(command.reportingData());

        // Messaging
        setMessagingData(command.messagingData());
    }

}
