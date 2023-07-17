package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;

public interface PageRuleService {

    CreateRuleResponse createPageRule(Long userId, CreateRuleRequest request) throws RuleException;

    CreateRuleResponse deletePageRule(Long ruleId);

    CreateRuleResponse updatePageRule(Long ruleId, CreateRuleRequest request, Long userId) throws RuleException;

    ViewRuleResponse getRuleResponse(Long ruleId);
}
