package gov.cdc.nbs.questionbank.pagerules;

import java.util.List;

import gov.cdc.nbs.questionbank.pagerules.Rule.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;

public class PageRuleCreateRequestHelper {



    public static CreateRuleRequest withSourceText(CreateRuleRequest request, final String sourceText) {
        return new CreateRuleRequest(
            request.function(),
            request.description(),
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.sourceValues(),
            request.comparator(),
            request.targetType(),
            request.targetIdentifiers(),
            sourceText,
            request.targetValueText());
    }

    public static CreateRuleRequest withSourceIdentifier(CreateRuleRequest request, final String sourceIdentifier) {
        return new CreateRuleRequest(
            request.function(),
            request.description(),
            sourceIdentifier,
            request.anySourceValue(),
            request.sourceValues(),
            request.comparator(),
            request.targetType(),
            request.targetIdentifiers(),
            request.sourceText(),
            request.targetValueText());
    }

    public static CreateRuleRequest withRuleDescription(CreateRuleRequest request, final String ruleDescription) {
        return new CreateRuleRequest(
            request.function(),
            ruleDescription,
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.sourceValues(),
            request.comparator(),
            request.targetType(),
            request.targetIdentifiers(),
            request.sourceText(),
            request.targetValueText());
    }

    public static CreateRuleRequest withFunction(CreateRuleRequest request, final String function) {
        return new CreateRuleRequest(
            Rule.RuleFunction.valueOf(function),
            request.description(),
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.sourceValues(),
            request.comparator(),
            request.targetType(),
            request.targetIdentifiers(),
            request.sourceText(),
            request.targetValueText());
    }

    public static CreateRuleRequest withComparator(CreateRuleRequest request, final String comparator) {
        return new CreateRuleRequest(
            request.function(),
            request.description(),
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.sourceValues(),
            Rule.Comparator.valueOf(comparator),
            request.targetType(),
            request.targetIdentifiers(),
            request.sourceText(),
            request.targetValueText());
    }

    public static CreateRuleRequest withAnySourceValue(CreateRuleRequest request, final boolean value) {
        return new CreateRuleRequest(
            request.function(),
            request.description(),
            request.sourceIdentifier(),
            value,
            request.sourceValues(),
            request.comparator(),
            request.targetType(),
            request.targetIdentifiers(),
            request.sourceText(),
            request.targetValueText());
    }

    public static CreateRuleRequest withTargetType(CreateRuleRequest request, final String targetType) {
        return new CreateRuleRequest(
            request.function(),
            request.description(),
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.sourceValues(),
            request.comparator(),
            Rule.TargetType.valueOf(targetType),
            request.targetIdentifiers(),
            request.sourceText(),
            request.targetValueText());
    }

    public static CreateRuleRequest withTargetValues(CreateRuleRequest request, List<String> values) {
        return new CreateRuleRequest(
            request.function(),
            request.description(),
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.sourceValues(),
            request.comparator(),
            request.targetType(),
            request.targetIdentifiers(),
            request.sourceText(),
            values);
    }

    public static CreateRuleRequest withTargetIdentifiers(CreateRuleRequest request, List<String> values) {
        return new CreateRuleRequest(
            request.function(),
            request.description(),
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.sourceValues(),
            request.comparator(),
            request.targetType(),
            values,
            request.sourceText(),
            request.targetValueText());
    }

    public static CreateRuleRequest withSourceValues(CreateRuleRequest request, List<SourceValue> values) {
        return new CreateRuleRequest(
            request.function(),
            request.description(),
            request.sourceIdentifier(),
            request.anySourceValue(),
            values,
            request.comparator(),
            request.targetType(),
            request.targetIdentifiers(),
            request.sourceText(),
            request.targetValueText());

    }

}
