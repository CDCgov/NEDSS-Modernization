package gov.cdc.nbs.questionbank.businessrules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

public interface BusinessRuleService {

   String createBusinessRule(CreateRuleRequest createRuleRequest);
}
