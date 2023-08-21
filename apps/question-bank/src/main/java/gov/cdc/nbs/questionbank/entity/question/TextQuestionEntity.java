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
@DiscriminatorValue(TextQuestionEntity.TEXT_QUESION_TYPE)
public class TextQuestionEntity extends WaQuestion {
    static final String TEXT_QUESION_TYPE = "TEXT";

    @Column(name = "mask", length = 50)
    private String mask;

    public void setMask(String mask) {
        this.mask = requireNonNull(mask, "Mask");
    }

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    public void setFieldSize(String fieldSize) {
        this.fieldSize = requireNonNull(fieldSize, "Field size");
    }

    @Column(name = "default_value", length = 300)
    private String defaultValue;

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDataType() {
        return TextQuestionEntity.TEXT_QUESION_TYPE;
    }

    public TextQuestionEntity(QuestionCommand.AddTextQuestion command) {
        super(command);

        setDefaultValue(command.defaultValue());
        setMask(command.mask());
        setFieldSize(command.fieldLength());

        // Audit
        created(command);

        // Reporting
        setReportingData(command);

        // Messaging
        setMessagingData(command.messagingData());
    }

    @Override
    public void update(QuestionCommand.Update command) {
        update(command.questionData());

        // Text specific fields
        setDefaultValue(command.defaultValue());
        setMask(command.mask());
        setFieldSize(command.fieldLength());


        // Reporting
        setReportingData(command);

        // Messaging
        setMessagingData(command.messagingData());

        // Audit
        changed(command);
    }

}
