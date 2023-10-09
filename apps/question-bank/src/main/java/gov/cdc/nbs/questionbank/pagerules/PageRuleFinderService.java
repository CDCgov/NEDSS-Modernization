package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageRuleFinderService {

    ViewRuleResponse getRuleResponse(Long ruleId);

    Page<ViewRuleResponse> getAllPageRule(Pageable pageRequest);

    Page<ViewRuleResponse> findPageRule(SearchPageRuleRequest request, Pageable pageable);
}
