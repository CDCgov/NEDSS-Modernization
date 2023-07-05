package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.response.RuleResponse;

import javax.management.BadAttributeValueExpException;

public interface PageRuleService {

   CreateRuleResponse createPageRule(Long userId,CreateRuleRequest.ruleRequest request) throws BadAttributeValueExpException;

//    RuleResponse getRuleByRuleId(String ruleId);

    CreateRuleResponse deletePageRule(Long ruleId);
}
