package gov.cdc.nbs.questionbank.businessrules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

public interface BusinessRuleService {

   Long createBusinessRule(CreateRuleRequest createRuleRequest);
}
