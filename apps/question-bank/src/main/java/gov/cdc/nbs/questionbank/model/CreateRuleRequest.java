package gov.cdc.nbs.questionbank.model;

import java.util.List;

public sealed interface CreateRuleRequest {
    Long templateUid();
   String ruleFunction();
   String ruleDescription();
   String sourceText();

   String sourceIdentifier();
   List<sourceValues> sourceValue();
   boolean anySourceValue();
   String comparator();
   String targetType();
   List<String> targetValueText();

   List<String> targetValueIdentifier();


    record ruleRequest(
            Long templateUid,
            String ruleFunction,
            String ruleDescription,
            String sourceText,
            String sourceIdentifier,
            List<sourceValues> sourceValue,

            boolean anySourceValue,
            String comparator,
            String targetType,
            List<String> targetValueText,

            List<String> targetValueIdentifier
            ) implements CreateRuleRequest{ }
    record sourceValues(List<String> sourceValueId, List<String> sourceValueText){}

}

