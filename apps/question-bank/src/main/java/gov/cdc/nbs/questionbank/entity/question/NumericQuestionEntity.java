package gov.cdc.nbs.questionbank.entity.question;

import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNonNull;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue(NumericQuestionEntity.NUMERIC_QUESION_TYPE)
public class NumericQuestionEntity extends WaQuestion {
    static final String NUMERIC_QUESION_TYPE = "NUMERIC";

    @Column(name = "mask", length = 50)
    private String mask;

    public void setMask(String mask) {
        this.mask = requireNonNull(mask, "Mask");
    }

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    public void setFieldSize(String fieldSize) {
        this.fieldSize = requireNonNull(fieldSize, "Field Length");
    }

    @Column(name = "default_value", length = 300)
    private String defaultValue;

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Column(name = "min_value")
    private Long minValue;

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    @Column(name = "max_value")
    private Long maxValue;

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    @Column(name = "unit_type_cd", length = 20)
    private String unitTypeCd; // Either Coded, Literal or null

    public void setUnitTypeCd(String unitTypeCd) {
        this.unitTypeCd = unitTypeCd;
    }

    @Column(name = "unit_value", length = 50)
    private String unitValue; // If unitTypeCd == Coded, Id of Value set. Else literal value or null

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    @Override
    public String getDataType() {
        return NumericQuestionEntity.NUMERIC_QUESION_TYPE;
    }

    public NumericQuestionEntity(QuestionCommand.AddNumericQuestion command) {
        super(command);

        setMask(command.mask());
        setFieldSize(command.fieldLength());
        setDefaultValue(command.defaultValue());
        setMinValue(command.minValue());
        setMaxValue(command.maxValue());
        setUnitTypeCd(command.unitTypeCd());
        if (unitTypeCd != null) {
            requireNonNull(command.unitValue(), "If specifying UnitType, UnitValue");
        }
        setUnitValue(command.unitValue());

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

        // Numeric fields
        setMask(command.mask());
        setFieldSize(command.fieldLength());
        setDefaultValue(command.defaultValue());
        setMinValue(command.minValue());
        setMaxValue(command.maxValue());
        if (command.unitType() != null) {
            setUnitTypeCd(command.unitType().toString());
            requireNonNull(command.unitValue(), "If specifying UnitType, UnitValue");
        }
        setUnitValue(command.unitValue());


        // Reporting
        setReportingData(command);

        // Messaging
        setMessagingData(command.messagingData());

        // Audit
        changed(command);
    }

}
