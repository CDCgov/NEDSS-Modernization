package gov.cdc.nbs.questionbank.pagerules.command;

import java.util.List;

public sealed interface RuleCommand {
    RuleData ruleData();

    public record AddTextRule(
            RuleData ruleData
    ) implements RuleCommand{}

    public record RuleData(
            String ruleFunction,
            String ruleDescription,

            String source,
            String sourceIdentifier,
            String sourceValue,

            boolean anySourceValue,
            String comparator,
            String targetType,
            List<String> targetValue
    ) {}


}
