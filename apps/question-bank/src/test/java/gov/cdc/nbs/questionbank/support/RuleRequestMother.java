package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

import java.util.ArrayList;
import java.util.List;


public class RuleRequestMother {

    public static CreateRuleRequest.ruleRequest ruleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date (INV213)");
        targetValuesList.add("Discharge Date (INV214)");
        return new CreateRuleRequest.ruleRequest(
                123456L,
                "TestRuleFunction",
                "TestDescription",
                "testSource (Test123)",
                "INV214",
                false,
                ">=",
                "testTargetValue",
                targetValuesList
                );
    }

    public static CreateRuleRequest.ruleRequest dateCompareRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date (INV213)");
        targetValuesList.add("Discharge Date (INV214)");
        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Date Compare",
                "TestDescription",
                "TestSource (Test123)",
                null,
                false,
                ">=",
                "testTargetValue",
                targetValuesList
        );
    }
}
