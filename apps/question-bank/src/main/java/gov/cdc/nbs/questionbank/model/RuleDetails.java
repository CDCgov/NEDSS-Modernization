package gov.cdc.nbs.questionbank.model;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.pagerules.command.RuleCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RuleDetails extends WaRuleMetadata{
    public RuleDetails(RuleCommand.AddTextRule command){
        super(command);
    }
}