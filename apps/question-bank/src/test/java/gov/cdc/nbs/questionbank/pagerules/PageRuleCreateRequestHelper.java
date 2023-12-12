package gov.cdc.nbs.questionbank.pagerules;

import java.util.List;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest.SourceValues;

public class PageRuleCreateRequestHelper {



    public static CreateRuleRequest withSourceText(CreateRuleRequest request, final String sourceText) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                sourceText,
                request.sourceIdentifier(),
                request.sourceValue(),
                request.anySourceValue(),
                request.comparator(),
                request.targetType(),
                request.targetValueText(),
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withSourceIdentifier(CreateRuleRequest request, final String sourceIdentifier) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                request.sourceText(),
                sourceIdentifier,
                request.sourceValue(),
                request.anySourceValue(),
                request.comparator(),
                request.targetType(),
                request.targetValueText(),
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withRuleDescription(CreateRuleRequest request, final String ruleDescription) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                ruleDescription,
                request.sourceText(),
                request.sourceIdentifier(),
                request.sourceValue(),
                request.anySourceValue(),
                request.comparator(),
                request.targetType(),
                request.targetValueText(),
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withFunction(CreateRuleRequest request, final String function) {
        return new CreateRuleRequest(
                function,
                request.ruleDescription(),
                request.sourceText(),
                request.sourceIdentifier(),
                request.sourceValue(),
                request.anySourceValue(),
                request.comparator(),
                request.targetType(),
                request.targetValueText(),
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withComparator(CreateRuleRequest request, final String comparator) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                request.sourceText(),
                request.sourceIdentifier(),
                request.sourceValue(),
                request.anySourceValue(),
                comparator,
                request.targetType(),
                request.targetValueText(),
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withAnySourceValue(CreateRuleRequest request, final boolean value) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                request.sourceText(),
                request.sourceIdentifier(),
                request.sourceValue(),
                value,
                request.comparator(),
                request.targetType(),
                request.targetValueText(),
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withTargetType(CreateRuleRequest request, final String targetType) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                request.sourceText(),
                request.sourceIdentifier(),
                request.sourceValue(),
                request.anySourceValue(),
                request.comparator(),
                targetType,
                request.targetValueText(),
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withTargetValues(CreateRuleRequest request, List<String> values) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                request.sourceText(),
                request.sourceIdentifier(),
                request.sourceValue(),
                request.anySourceValue(),
                request.comparator(),
                request.targetType(),
                values,
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withTargetIdentifiers(CreateRuleRequest request, List<String> values) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                request.sourceText(),
                request.sourceIdentifier(),
                request.sourceValue(),
                request.anySourceValue(),
                request.comparator(),
                request.targetType(),
                request.targetValueText(),
                values);
    }
    public static CreateRuleRequest withSourceValueId(CreateRuleRequest request, List<String> values) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                request.sourceText(),
                request.sourceIdentifier(),
                new SourceValues(values, request.sourceValue().sourceValueText()),
                request.anySourceValue(),
                request.comparator(),
                request.targetType(),
                request.targetValueText(),
                request.targetValueIdentifier());
    }
    public static CreateRuleRequest withSourceValueText(CreateRuleRequest request, List<String> values) {
        return new CreateRuleRequest(
                request.ruleFunction(),
                request.ruleDescription(),
                request.sourceText(),
                request.sourceIdentifier(),
                new SourceValues(request.sourceValue().sourceValueId(), values),
                request.anySourceValue(),
                request.comparator(),
                request.targetType(),
                request.targetValueText(),
                request.targetValueIdentifier());
    }
}
