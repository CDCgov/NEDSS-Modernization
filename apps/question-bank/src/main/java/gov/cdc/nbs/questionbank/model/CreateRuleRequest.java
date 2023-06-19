package gov.cdc.nbs.questionbank.model;

public sealed interface CreateRuleRequest {
   String ruleFunction();
   String ruleDescription();

   String anySourceValue();
   String comparator();

   String source();
   String sourceValue();
   String targetType();

   String targetValue();

    record ruleRequest(
            String ruleFunction,
            String ruleDescription,
            String source,
            String anySourceValue,
            String comparator,
            String sourceValue,
            String targetType,
            String targetValue) implements CreateRuleRequest{ }


}

