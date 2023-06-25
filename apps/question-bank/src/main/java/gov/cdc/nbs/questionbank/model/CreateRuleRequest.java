package gov.cdc.nbs.questionbank.model;

import java.util.List;

public sealed interface CreateRuleRequest {
    Long templateUid();
   String ruleFunction();
   String ruleDescription();
   String source();
   String sourceValue();
   boolean anySourceValue();
   String comparator();
   String targetType();
   List<String> targetValue();

    record ruleRequest(
            Long templateUid,
            String ruleFunction,
            String ruleDescription,
            String source,
            String sourceValue,

            boolean anySourceValue,
            String comparator,
            String targetType,
            List<String> targetValue
            ) implements CreateRuleRequest{ }

}

