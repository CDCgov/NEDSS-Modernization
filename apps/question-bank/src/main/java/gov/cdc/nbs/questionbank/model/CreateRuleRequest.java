package gov.cdc.nbs.questionbank.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateRuleRequest {
    private RuleDescriptionDto ruleDescription;
    private RuleDetailsDTO ruleDetails;
}
