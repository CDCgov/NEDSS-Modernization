package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RequireIfCommandCreator {

  // function name
  // source id
  // if - ($j.inArray('D',foo) > -1) || ($j.inArray('H',foo) > -1)
  // pgRequireElement
  // pgRequireNotElement
  private static final String SPECIFIC_SOURCE_VALUE =
      """
      function %s
      {
       var foo = [];
      $j('#%s :selected').each(function(i, selected){
       foo[i] = $j(selected).val();
       });
      if(foo.length==0) return;

       if(%s){
      %s
       } else {
      %s
       }
      }
        """;

  // function name
  // source id
  // pgRequireElement
  // pgRequireNotElement
  private static final String ANY_SOURCE_VALUE =
      """
      function %s
      {
       var foo = [];
      $j('#%s :selected').each(function(i, selected){
       foo[i] = $j(selected).val();
       });
      if(foo.length>0 && foo[0] != '') {
      %s
       } else {
      %s
       }
      }
        """;

  public PageContentCommand.AddRuleCommand create(
      long nextAvailableId, RuleRequest request, long page, long userId) {
    String functionName = createJavascriptName(request.sourceIdentifier(), nextAvailableId);
    String sourceValues = createSourceValues(request.anySourceValue(), request.sourceValues());
    String errorMessage =
        createErrorMessage(
            request.sourceText(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetValueText(),
            request.comparator().getValue());
    String javascript =
        createJavascript(
            functionName,
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.targetIdentifiers(),
            request.sourceValues(),
            request.comparator().getValue());
    String expression =
        createExpression(
            request.sourceIdentifier(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetIdentifiers(),
            request.comparator().getValue());

    return new PageContentCommand.AddRuleCommand(
        nextAvailableId,
        request.targetType().toString(),
        request.ruleFunction().getValue(),
        request.description(),
        request.comparator().getValue(),
        request.sourceIdentifier(),
        sourceValues,
        String.join(",", request.targetIdentifiers()),
        errorMessage,
        javascript,
        functionName,
        expression,
        page,
        userId,
        Instant.now());
  }

  public PageContentCommand.UpdateRuleCommand update(
      long currentId, RuleRequest request, long userId) {
    String functionName = createJavascriptName(request.sourceIdentifier(), currentId);
    String sourceValues = createSourceValues(request.anySourceValue(), request.sourceValues());
    String errorMessage =
        createErrorMessage(
            request.sourceText(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetValueText(),
            request.comparator().getValue());
    String javascript =
        createJavascript(
            functionName,
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.targetIdentifiers(),
            request.sourceValues(),
            request.comparator().getValue());
    String expression =
        createExpression(
            request.sourceIdentifier(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetIdentifiers(),
            request.comparator().getValue());

    return new PageContentCommand.UpdateRuleCommand(
        request.targetType().toString(),
        request.description(),
        request.comparator().getValue(),
        request.sourceIdentifier(),
        sourceValues,
        String.join(",", request.targetIdentifiers()),
        errorMessage,
        javascript,
        functionName,
        expression,
        userId,
        Instant.now());
  }

  String createSourceValues(boolean anySourceValue, List<SourceValue> sourceValues) {
    if (anySourceValue) {
      return "Any Source Value";
    } else {
      return sourceValues.stream().map(SourceValue::text).collect(Collectors.joining(", "));
    }
  }

  String createJavascriptName(String sourceIdentifier, long ruleId) {
    return "ruleRequireIf" + sourceIdentifier + ruleId + "()";
  }

  String createErrorMessage(
      String sourceLabel,
      List<SourceValue> sourceValues,
      boolean anySourceValue,
      List<String> targetLabels,
      String comparator) {
    String sourceValue =
        anySourceValue
            ? "Any Source Value"
            : sourceValues.stream().map(SourceValue::text).collect(Collectors.joining(", "));
    return "%s %s must be ( %s ) %s"
        .formatted(sourceLabel, comparator, sourceValue, String.join(", ", targetLabels));
  }

  String createExpression(
      String sourceIdentifier,
      List<SourceValue> sourceValues,
      boolean anySourceValue,
      List<String> targetIdentifiers,
      String comparator) {
    String values =
        anySourceValue
            ? ""
            : sourceValues.stream().map(SourceValue::id).collect(Collectors.joining(" , "));
    String comparatorValue = anySourceValue ? "" : comparator;
    String targetIdentifier = String.join(" , ", targetIdentifiers);
    return "%s ( %s ) %s ^ R ( %s )"
        .formatted(sourceIdentifier, values, comparatorValue, targetIdentifier);
  }

  String createJavascript(
      String functionName,
      String sourceIdentifier,
      boolean anySourceValue,
      List<String> targetIdentifiers,
      List<SourceValue> sourceValues,
      String comparator) {
    if (anySourceValue) {
      comparator = "=";
      return formatAnySourceValue(functionName, sourceIdentifier, comparator, targetIdentifiers);
    } else {
      return formatSpecificSourceValue(
          functionName, sourceIdentifier, comparator, targetIdentifiers, sourceValues);
    }
  }

  private String formatSpecificSourceValue(
      String functionName,
      String sourceIdentifier,
      String comparator,
      List<String> targetIdentifiers,
      List<SourceValue> sourceValues) {
    String requireCalls =
        targetIdentifiers.stream().map(this::require).collect(Collectors.joining("\n"));
    String requireNotCalls =
        targetIdentifiers.stream().map(this::requireNot).collect(Collectors.joining("\n"));

    return SPECIFIC_SOURCE_VALUE.formatted(
        functionName,
        sourceIdentifier,
        createIf(sourceValues),
        "=".equals(comparator) ? requireCalls : requireNotCalls,
        "=".equals(comparator) ? requireNotCalls : requireCalls);
  }

  private String formatAnySourceValue(
      String functionName,
      String sourceIdentifier,
      String comparator,
      List<String> targetIdentifiers) {
    String requireCalls =
        targetIdentifiers.stream().map(this::require).collect(Collectors.joining("\n"));
    String requireNotCalls =
        targetIdentifiers.stream().map(this::requireNot).collect(Collectors.joining("\n"));

    return ANY_SOURCE_VALUE.formatted(
        functionName,
        sourceIdentifier,
        "=".equals(comparator) ? requireCalls : requireNotCalls,
        "=".equals(comparator) ? requireNotCalls : requireCalls);
  }

  String require(String targetIdentifier) {
    return "pgRequireElement('%s');".formatted(targetIdentifier);
  }

  String requireNot(String targetIdentifier) {
    return "pgRequireNotElement('%s');".formatted(targetIdentifier);
  }

  String createIf(List<SourceValue> sourceValues) {
    return sourceValues.stream()
        .map(SourceValue::id)
        .map(this::identifierIfClause)
        .collect(Collectors.joining(" || "));
  }

  String identifierIfClause(String value) {
    return "($j.inArray('%s',foo) > -1)".formatted(value);
  }
}
