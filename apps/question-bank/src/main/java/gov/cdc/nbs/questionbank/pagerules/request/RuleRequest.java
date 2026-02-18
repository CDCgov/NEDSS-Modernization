package gov.cdc.nbs.questionbank.pagerules.request;

import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record RuleRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) RuleFunction ruleFunction,
    String description,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String sourceIdentifier,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean anySourceValue,
    List<SourceValue> sourceValues,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Comparator comparator,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) TargetType targetType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<String> targetIdentifiers,
    String sourceText,
    List<String> targetValueText) {}
