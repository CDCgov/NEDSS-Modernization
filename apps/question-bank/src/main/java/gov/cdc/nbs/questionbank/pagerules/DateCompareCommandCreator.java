package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DateCompareCommandCreator {
  // function name
  // source Identifier
  // source Identifier
  private static final String JAVASCRIPT =
      """
      function %s {
        var i = 0;
        var errorElts = new Array();
        var errorMsgs = new Array();

      if ((getElementByIdOrByName("%s").value)==''){
      return {elements : errorElts, labels : errorMsgs}; }
      var sourceStr =getElementByIdOrByName("%s").value;
      var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
      var targetElt;
      var targetStr = '';
      var targetDate = '';
         """;

  // target identifier
  // target identifier
  // comparator
  // source identifier
  // target identifier
  // source text
  // target text all or first?
  // comparator
  // target identifier
  private static final String JAVASCRIPT_TARGETS =
      """
      targetStr =getElementByIdOrByName("%s") == null ? "" :getElementByIdOrByName("%s").value;
      if (targetStr!="") {
         targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
      if (!(srcDate %s targetDate)) {
      var srcDateEle=getElementByIdOrByName("%s");
      var targetDateEle=getElementByIdOrByName("%s");
      var srca2str=buildErrorAnchorLink(srcDateEle,"%s");
      var targeta2str=buildErrorAnchorLink(targetDateEle,"%s");
        errorMsgs[i]=srca2str + " must be %s " + targeta2str;
        colorElementLabelRed(srcDateEle);
        colorElementLabelRed(targetDateEle);
      errorElts[i++]=getElementByIdOrByName("%s");
      }
        }
           """;

  private static final String JAVASCRIPT_CLOSE =
      " return {elements : errorElts, labels : errorMsgs}\n}\n";

  public PageContentCommand.UpdateRuleCommand update(
      long currentId, RuleRequest request, long userId) {
    String functionName = createJavascriptName(request.sourceIdentifier(), currentId);
    String errorMessage =
        createErrorMessage(
            request.sourceText(), request.targetValueText(), request.comparator().getValue());
    String javascript =
        createJavascript(
            functionName,
            request.sourceIdentifier(),
            request.sourceText(),
            request.targetIdentifiers(),
            request.targetValueText(),
            request.comparator().getValue());
    String expression =
        createExpression(
            request.sourceIdentifier(),
            request.targetIdentifiers(),
            request.comparator().getValue());

    return new PageContentCommand.UpdateRuleCommand(
        null,
        request.description(),
        request.comparator().getValue(),
        request.sourceIdentifier(),
        null,
        String.join(",", request.targetIdentifiers()),
        errorMessage,
        javascript,
        functionName,
        expression,
        userId,
        Instant.now());
  }

  public PageContentCommand.AddRuleCommand create(
      long nextAvailableId, RuleRequest request, long page, long userId) {
    String functionName = createJavascriptName(request.sourceIdentifier(), nextAvailableId);
    String errorMessage =
        createErrorMessage(
            request.sourceText(), request.targetValueText(), request.comparator().getValue());
    String javascript =
        createJavascript(
            functionName,
            request.sourceIdentifier(),
            request.sourceText(),
            request.targetIdentifiers(),
            request.targetValueText(),
            request.comparator().getValue());
    String expression =
        createExpression(
            request.sourceIdentifier(),
            request.targetIdentifiers(),
            request.comparator().getValue());

    return new PageContentCommand.AddRuleCommand(
        nextAvailableId,
        null,
        request.ruleFunction().getValue(),
        request.description(),
        request.comparator().getValue(),
        request.sourceIdentifier(),
        null,
        String.join(",", request.targetIdentifiers()),
        errorMessage,
        javascript,
        functionName,
        expression,
        page,
        userId,
        Instant.now());
  }

  String createJavascript(
      String functionName,
      String sourceIdentifier,
      String sourceLabel,
      List<String> targetIdentifiers,
      List<String> targetLabels,
      String comparator) {
    String first = JAVASCRIPT.formatted(functionName, sourceIdentifier, sourceIdentifier);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < targetIdentifiers.size(); i++) {
      String targetIdentifier = targetIdentifiers.get(i);
      String targetLabel = targetLabels.get(i);
      sb.append(
          JAVASCRIPT_TARGETS.formatted(
              targetIdentifier,
              targetIdentifier,
              comparator,
              sourceIdentifier,
              targetIdentifier,
              sourceLabel,
              targetLabel,
              comparator,
              targetIdentifier));
    }

    return first + sb + JAVASCRIPT_CLOSE;
  }

  String createJavascriptName(String sourceIdentifier, long ruleId) {
    return "ruleDComp" + sourceIdentifier + ruleId + "()";
  }

  String createExpression(
      String sourceIdentifier, List<String> targetIdentifiers, String comparator) {
    String targetIdentifier = String.join(" , ", targetIdentifiers);
    return "%s %s  ^ DT ( %s )".formatted(sourceIdentifier, comparator, targetIdentifier);
  }

  String createErrorMessage(String sourceLabel, List<String> targetLabels, String comparator) {
    return targetLabels.stream()
        .map(tl -> "%s  must be %s  %s".formatted(sourceLabel, comparator, tl))
        .collect(Collectors.joining(", "));
  }
}
