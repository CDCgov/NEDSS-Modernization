package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.pagerules.JSFunctionNameHelper;
import gov.cdc.nbs.questionbank.pagerules.RuleData;

public class RuleDataMother {

    public static RuleData ruleData() {
        JSFunctionNameHelper temp = new JSFunctionNameHelper("testFunction", "testFunctionName");

        return new RuleData(
                "testTargetId",
                "testExpression",
                "testErrorMsg",
                "testSourceId",
                "testSourceText",
                "testSourceValue",
                temp);
    }
}
