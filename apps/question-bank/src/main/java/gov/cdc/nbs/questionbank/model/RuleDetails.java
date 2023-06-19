package gov.cdc.nbs.questionbank.model;


import gov.cdc.nbs.questionbank.entity.businessRule.WaRuleMetadata;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RuleDetails extends WaRuleMetadata{
    public RuleDetails(CreateRuleRequest.ruleRequest ruleRequest){
        super(ruleRequest);
    }


}
