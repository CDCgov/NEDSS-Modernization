package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;

public interface PageRuleService {

   CreateRuleResponse createPageRule(Long userId,CreateRuleRequest.ruleRequest request);
}
