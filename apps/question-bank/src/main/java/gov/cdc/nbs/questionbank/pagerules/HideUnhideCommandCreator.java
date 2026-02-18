package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class HideUnhideCommandCreator {

  private static final String ANY_SOURCE_VALUE =
      """
          function %s
          {
           var foo = [];
          $j('#%s :selected').each(function(i, selected){
           foo[i] = $j(selected).val();
           });
          if(foo=='' && $j('#%s').html()!=null){foo[0]=$j('#%s').html().replace(/^\s+|\s+$/g,'');}if(foo.length>0 && foo[0] != '') {
          %s
           } else {
          %s
           }
           var foo_2 = [];
          $j('#%s_2 :selected').each(function(i, selected){
           foo_2[i] = $j(selected).val();
           });
          if(foo_2=='' && $j('#%s_2').html()!=null){foo_2[0]=$j('#%s_2').html().replace(/^\s+|\s+$/g,'');}if(foo_2.length>0 && foo_2[0] != '') {
          %s
           } else {
          %s
           }
          }
            """;

  private static final String SPECIFIC_SOURCE_VALUES =
      """
          function %s
          {
           var foo = [];
          $j('#%s :selected').each(function(i, selected){
           foo[i] = $j(selected).val();
           });
          if(foo=='' && $j('#%s').html()!=null){foo[0]=$j('#%s').html().replace(/^\s+|\s+$/g,'');}
           if(%s){
          %s
           } else {
          %s
           }
           var foo_2 = [];
          $j('#%s_2 :selected').each(function(i, selected){
           foo_2[i] = $j(selected).val();
           });
          if(foo_2=='' && $j('#%s_2').html()!=null){foo_2[0]=$j('#%s_2').html().replace(/^\s+|\s+$/g,'');}
           if(%s){
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
            request.sourceIdentifier(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetValueText(),
            request.comparator().getValue());
    String javascript = createJavascript(functionName, request);
    String expression =
        createExpression(
            request.sourceIdentifier(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetIdentifiers(),
            request.comparator().getValue(),
            RuleFunction.ENABLE.equals(request.ruleFunction()));

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
            request.sourceIdentifier(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetValueText(),
            request.comparator().getValue());
    String javascript = createJavascript(functionName, request);
    String expression =
        createExpression(
            request.sourceIdentifier(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetIdentifiers(),
            request.comparator().getValue(),
            RuleFunction.ENABLE.equals(request.ruleFunction()));

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
    return "ruleHideUnh" + sourceIdentifier + ruleId + "()";
  }

  String createErrorMessage(
      String sourceLabel,
      String sourceIdentifier,
      List<SourceValue> sourceValues,
      boolean anySourceValue,
      List<String> targetLabels,
      String comparator) {
    if (anySourceValue) {
      return "%s %s must be ( %s ) %s"
          .formatted(sourceLabel, comparator, "Any Source Value", String.join(", ", targetLabels));
    } else {
      return "%s (%s) %s must be ( %s ) %s"
          .formatted(
              sourceLabel,
              sourceIdentifier,
              comparator,
              sourceValues.stream().map(SourceValue::text).collect(Collectors.joining(" , ")),
              String.join(", ", targetLabels));
    }
  }

  String createExpression(
      String sourceIdentifier,
      List<SourceValue> sourceValues,
      boolean anySourceValue,
      List<String> targetIdentifiers,
      String comparator,
      boolean isHide) {
    String indicator = isHide ? "H" : "S";
    String values =
        anySourceValue
            ? ""
            : sourceValues.stream().map(SourceValue::id).collect(Collectors.joining(" , "));
    String comparatorValue = anySourceValue ? "" : comparator;
    String targetIdentifier = String.join(" , ", targetIdentifiers);
    return "%s ( %s ) %s ^ %s ( %s )"
        .formatted(sourceIdentifier, values, comparatorValue, indicator, targetIdentifier);
  }

  String createJavascript(String functionName, RuleRequest request) {
    if (request.anySourceValue()) {
      String comparator = "=";
      return formatAnySourceValue(
          functionName,
          request.sourceIdentifier(),
          RuleFunction.HIDE.equals(request.ruleFunction()),
          comparator,
          request.targetIdentifiers(),
          request.targetType());
    } else {
      return formatSpecificSourceValue(
          functionName,
          request.sourceIdentifier(),
          RuleFunction.HIDE.equals(request.ruleFunction()),
          request.comparator().getValue(),
          request.targetIdentifiers(),
          request.sourceValues(),
          request.targetType());
    }
  }

  private String formatSpecificSourceValue(
      String functionName,
      String sourceIdentifier,
      boolean isHide,
      String comparator,
      List<String> targetIdentifiers,
      List<SourceValue> sourceValues,
      TargetType targetType) {
    String unHideCalls =
        targetIdentifiers.stream()
            .map(t -> pgUnhide(t, targetType))
            .collect(Collectors.joining("\n"));
    String hideCalls =
        targetIdentifiers.stream()
            .map(t -> pgHide(t, targetType))
            .collect(Collectors.joining("\n"));
    String unHideCalls2 =
        targetIdentifiers.stream()
            .map(t -> pgUnhide2(t, targetType))
            .collect(Collectors.joining("\n"));
    String hideCalls2 =
        targetIdentifiers.stream()
            .map(t -> pgHide2(t, targetType))
            .collect(Collectors.joining("\n"));
    return SPECIFIC_SOURCE_VALUES.formatted(
        functionName,
        sourceIdentifier,
        sourceIdentifier,
        sourceIdentifier,
        createIf(sourceValues),
        hide(isHide, comparator) ? hideCalls : unHideCalls,
        hide(isHide, comparator) ? unHideCalls : hideCalls,
        sourceIdentifier,
        sourceIdentifier,
        sourceIdentifier,
        createIf2(sourceValues),
        hide(isHide, comparator) ? hideCalls2 : unHideCalls2,
        hide(isHide, comparator) ? unHideCalls2 : hideCalls2);
  }

  private String formatAnySourceValue(
      String functionName,
      String sourceIdentifier,
      boolean isHide,
      String comparator,
      List<String> targetIdentifiers,
      TargetType targetType) {
    String unHideCalls =
        targetIdentifiers.stream()
            .map(t -> pgUnhide(t, targetType))
            .collect(Collectors.joining("\n"));
    String hideCalls =
        targetIdentifiers.stream()
            .map(t -> pgHide(t, targetType))
            .collect(Collectors.joining("\n"));
    String unHideCalls2 =
        targetIdentifiers.stream()
            .map(t -> pgUnhide2(t, targetType))
            .collect(Collectors.joining("\n"));
    String hideCalls2 =
        targetIdentifiers.stream()
            .map(t -> pgHide2(t, targetType))
            .collect(Collectors.joining("\n"));
    return ANY_SOURCE_VALUE.formatted(
        functionName,
        sourceIdentifier,
        sourceIdentifier,
        sourceIdentifier,
        isHide && "=".equals(comparator) ? hideCalls : unHideCalls,
        isHide && "=".equals(comparator) ? unHideCalls : hideCalls,
        sourceIdentifier,
        sourceIdentifier,
        sourceIdentifier,
        isHide && "=".equals(comparator) ? hideCalls2 : unHideCalls2,
        isHide && "=".equals(comparator) ? unHideCalls2 : hideCalls2);
  }

  private boolean hide(boolean isHide, String comparator) {
    if (isHide) {
      return "=".equals(comparator);
    } else {
      return "<>".equals(comparator);
    }
  }

  String pgUnhide(String targetIdentifier, TargetType targetType) {
    String method =
        TargetType.QUESTION.equals(targetType) ? "pgUnhideElement" : "pgSubSectionShown";
    return "%s('%s');".formatted(method, targetIdentifier);
  }

  String pgHide(String targetIdentifier, TargetType targetType) {
    String method = TargetType.QUESTION.equals(targetType) ? "pgHideElement" : "pgSubSectionHidden";
    return "%s('%s');".formatted(method, targetIdentifier);
  }

  String pgUnhide2(String targetIdentifier, TargetType targetType) {
    String method =
        TargetType.QUESTION.equals(targetType) ? "pgUnhideElement" : "pgSubSectionShown";
    return "%s('%s_2');".formatted(method, targetIdentifier);
  }

  String pgHide2(String targetIdentifier, TargetType targetType) {
    String method = TargetType.QUESTION.equals(targetType) ? "pgHideElement" : "pgSubSectionHidden";
    return "%s('%s_2');".formatted(method, targetIdentifier);
  }

  String createIf(List<SourceValue> sourceValues) {
    List<String> clauses = new ArrayList<>();
    for (SourceValue sv : sourceValues) {
      clauses.add(identifierIfClause(sv.id()));
      clauses.add(labelIfClause(sv.text()));
    }
    return String.join(" || ", clauses);
  }

  String createIf2(List<SourceValue> sourceValues) {
    List<String> clauses = new ArrayList<>();
    for (SourceValue sv : sourceValues) {
      clauses.add(identifierIfClause2(sv.id()));
      clauses.add(labelIfClause2(sv.text()));
    }
    return String.join(" || ", clauses);
  }

  String identifierIfClause(String value) {
    return "($j.inArray('%s',foo) > -1)".formatted(value);
  }

  String labelIfClause(String label) {
    return "($j.inArray('%s'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'%s')==true)"
        .formatted(label, label);
  }

  String identifierIfClause2(String value) {
    return "($j.inArray('%s',foo_2) > -1)".formatted(value);
  }

  String labelIfClause2(String label) {
    return "($j.inArray('%s'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'%s')==true)"
        .formatted(label, label);
  }
}
