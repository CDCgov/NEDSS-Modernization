package gov.cdc.nbs.questionbank.pagerules.command;

public sealed interface RuleCommand {
    RuleData ruleData();

    public record AddTextRule(
            String mask,
            String fieldLength,
            String defaultValue,

            RuleData ruleData
    ) implements RuleCommand{}

    public record RuleData(
            String ruleFunction,
            String ruleDescription,
            String source,
            String anySourceValue,
            String comparator,
            String sourceValue,
            String targetType,
            String targetValue
    ){}


}
