package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;


public class RuleRequestMother {

    public static CreateRuleRequest.ruleRequest createRuleRequest(){
        return ruleRequest();
    }

    public static CreateRuleRequest.ruleRequest ruleRequest() {
        return new CreateRuleRequest.ruleRequest(
                "TestRuleFunction",
                "TestDescription",
                "testSource",
                "",
                "=>",
                "testSourceValue",
                "testTargetType",
                "testtargetValue");
    }
}
