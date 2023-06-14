package gov.cdc.nbs.questionbank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RuleDescriptionDto {
    private String ruleFunction;

    private String ruleDescription;
}
