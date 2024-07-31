package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DateCompareCommandCreatorTest {

  DateCompareCommandCreator creator = new DateCompareCommandCreator();

  @Test
  void function_name() {
    String expected = "ruleDCompINV14411()";
    String actual = creator.createJavascriptName(
        "INV144",
        11);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void generates_valid_expression() {
    String expected = "INV132 <  ^ DT ( INV162 , INV110 )";
    String expression = creator.createExpression(
        "INV132",
        List.of("INV162 , INV110"),
        "<");
    assertThat(expression).isEqualTo(expected);
  }

  @Test
  void generates_valid_expression_single() {
    String expected = "INV132 <  ^ DT ( INV162 )";
    String expression = creator.createExpression(
        "INV132",
        List.of("INV162"),
        "<");
    assertThat(expression).isEqualTo(expected);
  }

  @Test
  void generates_valid_error_message() {
    String expected =
        "Admission Date  must be >  Confirmation Date, Admission Date  must be >  Date Assigned to Investigation";
    String errorMessage = creator.createErrorMessage(
        "Admission Date",
        Arrays.asList("Confirmation Date", "Date Assigned to Investigation"),
        ">");
    assertThat(errorMessage).isEqualTo(expected);
  }

  @Test
  void generates_valid_error_message_single() {
    String expected =
        "Admission Date  must be <=  Confirmation Date";
    String errorMessage = creator.createErrorMessage(
        "Admission Date",
        List.of("Confirmation Date"),
        "<=");
    assertThat(errorMessage).isEqualTo(expected);
  }


  @Test
  void generates_valid_javascript_name() {
    final String expected = "ruleDCompINV13210()";
    String functionName = creator.createJavascriptName(
        "INV132",
        10);
    assertThat(functionName).isEqualTo(expected);
  }

  @Test
  void generates_valid_javascript() {
    final String expected = """
        function ruleDCompINV13210() {
          var i = 0;
          var errorElts = new Array();
          var errorMsgs = new Array();

        if ((getElementByIdOrByName("INV132").value)==''){
        return {elements : errorElts, labels : errorMsgs}; }
        var sourceStr =getElementByIdOrByName("INV132").value;
        var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
        var targetElt;
        var targetStr = '';
        var targetDate = '';
        targetStr =getElementByIdOrByName("INV162") == null ? "" :getElementByIdOrByName("INV162").value;
        if (targetStr!="") {
           targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
        if (!(srcDate < targetDate)) {
        var srcDateEle=getElementByIdOrByName("INV132");
        var targetDateEle=getElementByIdOrByName("INV162");
        var srca2str=buildErrorAnchorLink(srcDateEle,"Admission Date");
        var targeta2str=buildErrorAnchorLink(targetDateEle,"Confirmation Date");
          errorMsgs[i]=srca2str + " must be < " + targeta2str;
          colorElementLabelRed(srcDateEle);
          colorElementLabelRed(targetDateEle);
        errorElts[i++]=getElementByIdOrByName("INV162");
        }
          }
        targetStr =getElementByIdOrByName("INV110") == null ? "" :getElementByIdOrByName("INV110").value;
        if (targetStr!="") {
           targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
        if (!(srcDate < targetDate)) {
        var srcDateEle=getElementByIdOrByName("INV132");
        var targetDateEle=getElementByIdOrByName("INV110");
        var srca2str=buildErrorAnchorLink(srcDateEle,"Admission Date");
        var targeta2str=buildErrorAnchorLink(targetDateEle,"Date Assigned to Investigation");
          errorMsgs[i]=srca2str + " must be < " + targeta2str;
          colorElementLabelRed(srcDateEle);
          colorElementLabelRed(targetDateEle);
        errorElts[i++]=getElementByIdOrByName("INV110");
        }
          }
         return {elements : errorElts, labels : errorMsgs}
        }
        """;
    String functionName = creator.createJavascriptName(
        "INV132",
        10);
    String javascript = creator.createJavascript(
        functionName,
        "INV132",
        "Admission Date",
        Arrays.asList("INV162", "INV110"),
        Arrays.asList("Confirmation Date", "Date Assigned to Investigation"),
        "<");

    assertThat(javascript).isEqualTo(expected);
  }

  @Test
  void generates_valid_javascript_single() {
    final String expected = """
        function ruleDCompINV13210() {
          var i = 0;
          var errorElts = new Array();
          var errorMsgs = new Array();

        if ((getElementByIdOrByName("INV132").value)==''){
        return {elements : errorElts, labels : errorMsgs}; }
        var sourceStr =getElementByIdOrByName("INV132").value;
        var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
        var targetElt;
        var targetStr = '';
        var targetDate = '';
        targetStr =getElementByIdOrByName("INV162") == null ? "" :getElementByIdOrByName("INV162").value;
        if (targetStr!="") {
           targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
        if (!(srcDate >= targetDate)) {
        var srcDateEle=getElementByIdOrByName("INV132");
        var targetDateEle=getElementByIdOrByName("INV162");
        var srca2str=buildErrorAnchorLink(srcDateEle,"Admission Date");
        var targeta2str=buildErrorAnchorLink(targetDateEle,"Confirmation Date");
          errorMsgs[i]=srca2str + " must be >= " + targeta2str;
          colorElementLabelRed(srcDateEle);
          colorElementLabelRed(targetDateEle);
        errorElts[i++]=getElementByIdOrByName("INV162");
        }
          }
         return {elements : errorElts, labels : errorMsgs}
        }
        """;
    String functionName = creator.createJavascriptName(
        "INV132",
        10);
    String javascript = creator.createJavascript(
        functionName,
        "INV132",
        "Admission Date",
        List.of("INV162"),
        List.of("Confirmation Date"),
        ">=");

    assertThat(javascript).isEqualTo(expected);
  }

  @Test
  void creates_command() {
    RuleRequest request = new RuleRequest(
        RuleFunction.DATE_COMPARE,
        "description",
        "INV154",
        false,
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours"),
            new SourceValue("N", "Minutes")),
        Comparator.EQUAL_TO,
        TargetType.QUESTION,
        Arrays.asList("DEM161", "DEM196"),
        "source text",
        Arrays.asList("target label1", "target label2"));
    PageContentCommand.AddRuleCommand command = creator.create(1887L, request, 3L, 9L);
    assertThat(command).isNotNull();
    assertThat(command.targetType()).isNull();
    assertThat(command.ruleFunction()).isEqualTo("Date Compare");
    assertThat(command.description()).isEqualTo("description");
    assertThat(command.comparator()).isEqualTo("=");
    assertThat(command.sourceIdentifier()).isEqualTo(request.sourceIdentifier());
    assertThat(command.page()).isEqualTo(3L);
    assertThat(command.userId()).isEqualTo(9L);
    assertThat(command.ruleId()).isEqualTo(1887L);
  }


  @Test
  void update_command() {
    RuleRequest request = new RuleRequest(
        RuleFunction.DATE_COMPARE,
        "description",
        "INV154",
        false,
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours"),
            new SourceValue("N", "Minutes")),
        Comparator.EQUAL_TO,
        TargetType.QUESTION,
        Arrays.asList("DEM161", "DEM196"),
        "source text",
        Arrays.asList("target label1", "target label 2"));
    PageContentCommand.UpdateRuleCommand command = creator.update(1887L, request, 3L);
    assertThat(command).isNotNull();
    assertThat(command.targetType()).isNull();
    assertThat(command.description()).isEqualTo("description");
    assertThat(command.comparator()).isEqualTo("=");
    assertThat(command.sourceIdentifier()).isEqualTo(request.sourceIdentifier());
    assertThat(command.targetIdentifiers()).isEqualTo("DEM161,DEM196");
    assertThat(command.userId()).isEqualTo(3L);
  }
}
