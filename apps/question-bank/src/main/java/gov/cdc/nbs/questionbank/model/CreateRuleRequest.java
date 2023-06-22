package gov.cdc.nbs.questionbank.model;

import java.util.List;

public sealed interface CreateRuleRequest {
   String ruleFunction();
   String ruleDescription();
   String source();
   String sourceIdentifier();
   String sourceValue();
   boolean anySourceValue();
   String comparator();
   String targetType();
   List<String> targetValue();

    record ruleRequest(
            String ruleFunction,
            String ruleDescription,
            String source,
            String sourceIdentifier,
            String sourceValue,
            boolean anySourceValue,
            String comparator,
            String targetType,
            List<String> targetValue
            ) implements CreateRuleRequest{ }

}

