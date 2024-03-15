package gov.cdc.nbs.questionbank.pagerules;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;

@Component
public class EnableDisableCreator {

  // function name
  // source identifier
  // pgEnable with target identifier
  // pgDisable with target identifier
  private static final String ANY_SOURCE_VALUE = """
      function %s()
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

  // function name
  // source identifier
  // big if
  // pgEnable with target identifier
  // pgDisable with target identifier
  private static final String SPECIFIC_SOURCE_VALUES =
      """
          function %s()
          {
           var foo = [];
          $j('#%s :selected').each(function(i, selected){
           foo[i] = $j(selected).val();
           });

           if(%s){
          %s
           } else {
          %s
           }
          }
            """;


  public PageContentCommand.UpdateRuleCommand update(long currentId, RuleRequest request, long userId) {
    String targetIdentifier = String.join(" , ", request.targetIdentifiers());
    String functionName = createJavascriptName(request.sourceIdentifier(), currentId);
    String sourceValues = createSourceValues(request.anySourceValue(), request.sourceValues());
    String errorMessage = createErrorMessage(
        request.sourceText(),
        request.sourceValues(),
        request.anySourceValue(),
        request.targetValueText(),
        request.comparator().getValue());
    String javascript = createJavascript(
        functionName,
        request.sourceIdentifier(),
        request.anySourceValue(),
        request.targetIdentifiers(),
        request.sourceValues(),
        request.comparator().getValue());
    String expression = createExpression(
        request.sourceIdentifier(),
        request.sourceValues(),
        request.anySourceValue(),
        targetIdentifier,
        request.comparator().getValue(),
        RuleFunction.ENABLE.equals(request.ruleFunction()));

    return new PageContentCommand.UpdateRuleCommand(
        request.targetType().toString(),
        request.description(),
        request.comparator().toString(),
        request.sourceIdentifier(),
        sourceValues,
        targetIdentifier,
        errorMessage,
        javascript,
        functionName,
        expression,
        userId,
        Instant.now());
  }

  public PageContentCommand.AddRuleCommand create(long nextAvailableId, RuleRequest request, long page, long userId) {
    String targetIdentifier = String.join(" , ", request.targetIdentifiers());
    String functionName = createJavascriptName(request.sourceIdentifier(), nextAvailableId);
    String sourceValues = createSourceValues(request.anySourceValue(), request.sourceValues());
    String errorMessage = createErrorMessage(
        request.sourceText(),
        request.sourceValues(),
        request.anySourceValue(),
        request.targetValueText(),
        request.comparator().getValue());
    String javascript = createJavascript(
        functionName,
        request.sourceIdentifier(),
        request.anySourceValue(),
        request.targetIdentifiers(),
        request.sourceValues(),
        request.comparator().getValue());
    String expression = createExpression(
        request.sourceIdentifier(),
        request.sourceValues(),
        request.anySourceValue(),
        targetIdentifier,
        request.comparator().getValue(),
        RuleFunction.ENABLE.equals(request.ruleFunction()));

    return new PageContentCommand.AddRuleCommand(
        nextAvailableId,
        request.targetType().toString(),
        request.ruleFunction().toString(),
        request.description(),
        request.comparator().toString(),
        request.sourceIdentifier(),
        sourceValues,
        targetIdentifier,
        errorMessage,
        javascript,
        functionName,
        expression,
        page,
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
    return "ruleEnDis" + sourceIdentifier + ruleId;
  }

  String createErrorMessage(
      String sourceLabel,
      List<SourceValue> sourceValues,
      boolean anySourceValue,
      List<String> targetLabels,
      String comparator) {
    String comparatorValue = anySourceValue ? "" : comparator;
    String sourceValue = anySourceValue ? "Any Source Value"
        : sourceValues.stream()
            .map(SourceValue::text)
            .collect(Collectors.joining(" , "));
    return String.format("%s %s must be ( %s ) %s",
        sourceLabel,
        comparatorValue,
        sourceValue,
        String.join(", ", targetLabels));
  }

  String createExpression(
      String sourceIdentifier,
      List<SourceValue> sourceValues,
      boolean anySourceValue,
      String targetIdentifier,
      String comparator,
      boolean isEnable) {
    String indicator = isEnable ? "E" : "D";
    String values = anySourceValue ? "" : sourceValues.stream().map(SourceValue::id).collect(Collectors.joining(" , "));
    String comparatorValue = anySourceValue ? "" : comparator;
    return String.format("%s ( %s ) %s ^ %s ( %s )",
        sourceIdentifier,
        values,
        comparatorValue,
        indicator,
        targetIdentifier);
  }

  String createJavascript(
      String functionName,
      String sourceIdentifier,
      boolean anySourceValue,
      List<String> targetIdentifiers,
      List<SourceValue> sourceValues,
      String comparator) {
    String enableCalls = targetIdentifiers.stream()
        .map(this::pgEnabled)
        .collect(Collectors.joining("\n"));
    String disableCalls = targetIdentifiers.stream()
        .map(this::pgDisabled)
        .collect(Collectors.joining("\n"));

    if (anySourceValue) {
      return String.format(ANY_SOURCE_VALUE,
          functionName,
          sourceIdentifier,
          "=".equals(comparator) ? enableCalls : disableCalls,
          "=".equals(comparator) ? disableCalls : enableCalls);
    } else {
      return String.format(SPECIFIC_SOURCE_VALUES,
          functionName,
          sourceIdentifier,
          createIf(sourceValues),
          "=".equals(comparator) ? disableCalls : enableCalls,
          "=".equals(comparator) ? enableCalls : disableCalls);
    }
  }

  String createIf(List<SourceValue> sourceValues) {
    List<String> clauses = new ArrayList<>();
    for (SourceValue sv : sourceValues) {
      clauses.add(identifierIfClause(sv.id()));
      clauses.add(labelIfClause(sv.text()));
    }
    return String.join(" || ", clauses);
  }

  String pgEnabled(String targetIdentifier) {
    return String.format("pgEnableElement('%s');", targetIdentifier);
  }

  String pgDisabled(String targetIdentifier) {
    return String.format("pgDisableElement('%s');", targetIdentifier);
  }

  String identifierIfClause(String value) {
    return String.format("($j.inArray('%s',foo) > -1)", value);
  }

  String labelIfClause(String label) {
    return String.format("($j.inArray('%s'.replace(/^\s+|\s+$/g,''),foo) > -1)", label);
  }

}
