package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

import java.math.BigInteger;

public interface PageRuleService {

   BigInteger createPageRule(CreateRuleRequest.ruleRequest request);
}
