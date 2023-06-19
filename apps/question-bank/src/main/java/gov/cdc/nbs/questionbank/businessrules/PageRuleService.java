package gov.cdc.nbs.questionbank.businessrules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

public interface PageRuleService {

   Long createPageRule(CreateRuleRequest.ruleRequest request);
}
