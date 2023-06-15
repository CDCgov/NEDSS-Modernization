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
@DiscriminatorValue(NumericQuestionEntity.NUMERIC_QUESION_TYPE)
public class NumericQuestionEntity extends WaQuestion {
    static final String NUMERIC_QUESION_TYPE = "NUMERIC";

    @Column(name = "mask", length = 50)
    private String mask;

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    @Column(name = "default_value", length = 300)
    private String defaultValue;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "unit_type_cd", length = 20)
    private String unitTypeCd;

    @Column(name = "unit_value", length = 50)
    private String unitValue;

    @Override
    public String getDataType() {
        return NumericQuestionEntity.NUMERIC_QUESION_TYPE;
    }

    public NumericQuestionEntity(QuestionCommand.AddNumericQuestion command) {
        super(command);

        this.mask = command.mask();
        this.fieldSize = command.fieldLength();
        this.defaultValue = command.defaultValue();
        this.minValue = command.minValue();
        this.maxValue = command.maxValue();
        this.unitTypeCd = command.unitTypeCd();
        this.unitValue = command.unitValue();

        // Audit
        created(command);

        // Reporting
        setReportingData(command.reportingData());

        // Messaging
        setMessagingData(command.messagingData());
    }

}
