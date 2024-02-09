package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;

public interface PageRuleService {

    CreateRuleResponse updatePageRule(Long ruleId, CreateRuleRequest request, Long userId,Long page) throws RuleException;

}
