package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;


public interface PageRuleService {


  ViewRuleResponse updatePageRule(Long ruleId, CreateRuleRequest request, Long userId, Long page) throws RuleException;

}
