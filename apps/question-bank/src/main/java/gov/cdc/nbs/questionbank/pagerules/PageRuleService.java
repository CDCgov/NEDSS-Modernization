package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

public interface PageRuleService {

   Long createPageRule(CreateRuleRequest.ruleRequest request);
}
