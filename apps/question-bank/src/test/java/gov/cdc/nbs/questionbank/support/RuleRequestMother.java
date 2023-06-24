package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

import java.util.ArrayList;
import java.util.List;


public class RuleRequestMother {

    public static CreateRuleRequest.ruleRequest ruleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("TestTargetValue");
        targetValuesList.add("testTarget1");
        return new CreateRuleRequest.ruleRequest(
                "TestRuleFunction",
                "TestDescription",
                "testSource",
                "INV214",
                "Yes",
                false,
                ">=",
                "testTargetValue",
                targetValuesList
                );
    }
}
