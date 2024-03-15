package gov.cdc.nbs.questionbank.pagerules;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;

@Component
public class HideUnhideCreator {

  private static final String ANY_SOURCE_VALUE =
      """
          function %s()
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
          function %s()
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

  public WaRuleMetadata create(long nextAvailableId, RuleRequest request, long page, long userId) {
    String targetIdentifier = String.join(" , ", request.targetIdentifiers());
    String functionName = createJavascriptName(request.sourceIdentifier(), nextAvailableId);
    PageContentCommand.AddHideUnhideRule command = new PageContentCommand.AddHideUnhideRule(
        nextAvailableId,
        request.targetType().toString(),
        request.ruleFunction().toString(),
        request.description(),
        request.comparator().toString(),
        request.sourceIdentifier(),
        createSourceValues(request.anySourceValue(), request.sourceValues()),
        targetIdentifier,
        createErrorMessage(
            request.sourceText(),
            request.sourceValues(),
            request.anySourceValue(),
            request.targetValueText(),
            request.comparator().getValue()),
        createJavascript(
            functionName,
            request.sourceIdentifier(),
            request.anySourceValue(),
            request.targetIdentifiers(),
            request.sourceValues(),
            request.comparator().getValue(),
            RuleFunction.HIDE.equals(request.ruleFunction())),
        functionName,
        createExpression(
            request.sourceIdentifier(),
            request.sourceValues(),
            request.anySourceValue(),
            targetIdentifier,
            request.comparator().getValue(),
            RuleFunction.ENABLE.equals(request.ruleFunction())),
        page,
        userId,
        Instant.now());
    return new WaRuleMetadata(command);
  }

  String createSourceValues(boolean anySourceValue, List<SourceValue> sourceValues) {
    if (anySourceValue) {
      return "Any Source Value";
    } else {
      return sourceValues.stream().map(SourceValue::text).collect(Collectors.joining(", "));
    }
  }

  String createJavascriptName(String sourceIdentifier, long ruleId) {
    return "ruleHideUnh" + sourceIdentifier + ruleId;
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
      boolean isHide) {
    String indicator = isHide ? "H" : "S";
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
      String comparator,
      boolean isHide) {
    if (anySourceValue) {
      comparator = "=";
      return formatAnySourceValue(
          functionName,
          sourceIdentifier,
          isHide,
          comparator,
          targetIdentifiers);
    } else {
      return formatSpecificSourceValue(
          functionName,
          sourceIdentifier,
          isHide,
          comparator,
          targetIdentifiers,
          sourceValues);
    }
  }

  private String formatSpecificSourceValue(
      String functionName,
      String sourceIdentifier,
      boolean isHide,
      String comparator,
      List<String> targetIdentifiers,
      List<SourceValue> sourceValues) {
    String unHideCalls = targetIdentifiers.stream()
        .map(this::pgUnhide)
        .collect(Collectors.joining("\n"));
    String hideCalls = targetIdentifiers.stream()
        .map(this::pgHide)
        .collect(Collectors.joining("\n"));
    String unHideCalls2 = targetIdentifiers.stream()
        .map(this::pgUnhide2)
        .collect(Collectors.joining("\n"));
    String hideCalls2 = targetIdentifiers.stream()
        .map(this::pgHide2)
        .collect(Collectors.joining("\n"));
    return String.format(SPECIFIC_SOURCE_VALUES,
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
      List<String> targetIdentifiers) {
    String unHideCalls = targetIdentifiers.stream()
        .map(this::pgUnhide)
        .collect(Collectors.joining("\n"));
    String hideCalls = targetIdentifiers.stream()
        .map(this::pgHide)
        .collect(Collectors.joining("\n"));
    String unHideCalls2 = targetIdentifiers.stream()
        .map(this::pgUnhide2)
        .collect(Collectors.joining("\n"));
    String hideCalls2 = targetIdentifiers.stream()
        .map(this::pgHide2)
        .collect(Collectors.joining("\n"));
    return String.format(ANY_SOURCE_VALUE,
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

  String pgUnhide(String targetIdentifier) {
    return String.format("pgUnhideElement('%s');", targetIdentifier);
  }

  String pgHide(String targetIdentifier) {
    return String.format("pgHideElement('%s');", targetIdentifier);
  }

  String pgUnhide2(String targetIdentifier) {
    return String.format("pgUnhideElement('%s_2');", targetIdentifier);
  }

  String pgHide2(String targetIdentifier) {
    return String.format("pgHideElement('%s_2');", targetIdentifier);
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
    return String.format("($j.inArray('%s',foo) > -1)", value);
  }

  String labelIfClause(String label) {
    return String.format("($j.inArray('%s'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'%s')==true)",
        label, label);
  }

  String identifierIfClause2(String value) {
    return String.format("($j.inArray('%s',foo_2) > -1)", value);
  }

  String labelIfClause2(String label) {
    return String.format("($j.inArray('%s'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'%s')==true)",
        label, label);
  }

}
