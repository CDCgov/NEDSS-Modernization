package gov.cdc.nbs.questionbank.model;


import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.pagerules.command.RuleCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static gov.cdc.nbs.questionbank.question.util.QuestionUtil.requireNonNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(RuleDetails.TEXT_RULE_TYPE)
public class RuleDetails extends WaRuleMetadata{

    static final String TEXT_RULE_TYPE = "TEXT";
    @Column(name = "mask", length = 50)
    private String mask;

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    @Column(name = "default_value", length = 300)
    private String defaultValue;
    @Override
    public String getDataType() {
        return RuleDetails.TEXT_RULE_TYPE;
    }
    public RuleDetails(RuleCommand.AddTextRule command){
        super(command);

        this.defaultValue = command.defaultValue();
        this.mask = requireNonNull(command.mask(), "Mask must not be null");
        this.fieldSize = requireNonNull(command.fieldLength(), "Field length must not be null");
    }


}
