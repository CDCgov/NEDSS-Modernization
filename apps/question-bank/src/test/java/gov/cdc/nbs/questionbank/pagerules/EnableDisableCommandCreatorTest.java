package gov.cdc.nbs.questionbank.pagerules;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class EnableDisableCommandCreatorTest {

  EnableDisableCommandCreator creator = new EnableDisableCommandCreator();

  @Test
  void function_name() {
    String expected = "ruleEnDisINV14411()";
    String actual = creator.createJavascriptName("INV144", 11);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void source_values_any() {
    String expected = "Any Source Value";
    String actual = creator.createSourceValues(true, null);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void source_value() {
    String expected = "text, text2";
    String actual =
        creator.createSourceValues(
            false, Arrays.asList(new SourceValue("id", "text"), new SourceValue("id2", "text2")));
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void error_message() {
    String expected = "Age at Onset Units <> must be ( Days, Hours ) Comments";
    String actual =
        creator.createErrorMessage(
            "Age at Onset Units",
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            false,
            Arrays.asList("Comments"),
            "<>");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void error_message_any_source() {
    String expected = "Age at Onset Units  must be ( Any Source Value ) Comments";
    String actual =
        creator.createErrorMessage(
            "Age at Onset Units",
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            true,
            Arrays.asList("Comments"),
            ">");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_equal_enable() {
    String expected = "INV144 ( D , H ) = ^ E ( INV515 )";
    String actual =
        creator.createExpression(
            "INV144",
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            false,
            Arrays.asList("INV515"),
            "=",
            true);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_equal_disable() {
    String expected = "INV144 ( D , H ) = ^ D ( INV515 )";
    String actual =
        creator.createExpression(
            "INV144",
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            false,
            Arrays.asList("INV515"),
            "=",
            false);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_not_equal_disable() {
    String expected = "INV144 ( D , H ) <> ^ D ( INV515 )";
    String actual =
        creator.createExpression(
            "INV144",
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            false,
            Arrays.asList("INV515"),
            "<>",
            false);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_not_equal_enable() {
    String expected = "INV144 ( D , H ) <> ^ E ( INV515 )";
    String actual =
        creator.createExpression(
            "INV144",
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            false,
            Arrays.asList("INV515"),
            "<>",
            true);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_any_source() {
    String expected = "INV144 (  )  ^ E ( INV515 )";
    String actual =
        creator.createExpression(
            "INV144",
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            true,
            Arrays.asList("INV515"),
            "<>",
            true);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_create_javascript_equals() {
    final String expected =
        """
            function ruleEnDisINV14411()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });

             if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('H',foo) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo) > -1)){
            pgDisableElement('INV515');
             } else {
            pgEnableElement('INV515');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV144", 11);
    String actual =
        creator.createJavascript(
            functionName,
            "INV144",
            false,
            Arrays.asList("INV515"),
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            "=",
            TargetType.QUESTION);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_create_javascript_not_equals() {
    final String expected =
        """
            function ruleEnDisINV14411()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });

             if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('H',foo) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo) > -1)){
            pgEnableElement('INV515');
             } else {
            pgDisableElement('INV515');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV144", 11);
    String actual =
        creator.createJavascript(
            functionName,
            "INV144",
            false,
            Arrays.asList("INV515"),
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            "<>",
            TargetType.QUESTION);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_create_javascript_any() {
    final String expected =
        """
            function ruleEnDisINV14411()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo.length>0 && foo[0] != '') {
            pgEnableElement('NBS102');
            pgEnableElement('INV143');
             } else {
            pgDisableElement('NBS102');
            pgDisableElement('INV143');
             }
            }
                """;
    String functionName = creator.createJavascriptName("INV144", 11);
    String actual =
        creator.createJavascript(
            functionName,
            "INV144",
            true,
            Arrays.asList("NBS102", "INV143"),
            null,
            "=",
            TargetType.QUESTION);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_create_javascript_any_not_equals() {
    final String expected =
        """
            function ruleEnDisINV14411()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo.length>0 && foo[0] != '') {
            pgDisableElement('NBS102');
            pgDisableElement('INV143');
             } else {
            pgEnableElement('NBS102');
            pgEnableElement('INV143');
             }
            }
                """;
    String functionName = creator.createJavascriptName("INV144", 11);
    String actual =
        creator.createJavascript(
            functionName,
            "INV144",
            true,
            Arrays.asList("NBS102", "INV143"),
            null,
            "<>",
            TargetType.QUESTION);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_create_javascript_subsection() {
    final String expected =
        """
            function ruleEnDisINV14411()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });

             if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('H',foo) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo) > -1)){
            pgSubSectionDisabled('NBS_UI_29');
            pgSubSectionDisabled('NBS_INV_GENV2_UI_5');
            pgSubSectionDisabled('NBS_UI_2');
             } else {
            pgSubSectionEnabled('NBS_UI_29');
            pgSubSectionEnabled('NBS_INV_GENV2_UI_5');
            pgSubSectionEnabled('NBS_UI_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV144", 11);
    String actual =
        creator.createJavascript(
            functionName,
            "INV144",
            false,
            Arrays.asList("NBS_UI_29", "NBS_INV_GENV2_UI_5", "NBS_UI_2"),
            Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
            "=",
            TargetType.SUBSECTION);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void creates_command() {
    RuleRequest request =
        new RuleRequest(
            RuleFunction.ENABLE,
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
            Arrays.asList());
    PageContentCommand.AddRuleCommand command = creator.create(1887l, request, 3l, 9l);
    assertThat(command).isNotNull();
    assertThat(command.targetType()).isEqualTo("QUESTION");
    assertThat(command.ruleFunction()).isEqualTo("Enable");
    assertThat(command.description()).isEqualTo("description");
    assertThat(command.comparator()).isEqualTo("=");
    assertThat(command.sourceIdentifier()).isEqualTo(request.sourceIdentifier());
    assertThat(command.page()).isEqualTo(3l);
    assertThat(command.userId()).isEqualTo(9l);
    assertThat(command.ruleId()).isEqualTo(1887l);
  }

  @Test
  void update_command() {
    RuleRequest request =
        new RuleRequest(
            RuleFunction.ENABLE,
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
            Arrays.asList());
    PageContentCommand.UpdateRuleCommand command = creator.update(1887l, request, 3l);
    assertThat(command).isNotNull();
    assertThat(command.targetType()).isEqualTo("QUESTION");
    assertThat(command.description()).isEqualTo("description");
    assertThat(command.comparator()).isEqualTo("=");
    assertThat(command.sourceIdentifier()).isEqualTo(request.sourceIdentifier());
    assertThat(command.targetIdentifiers()).isEqualTo("DEM161,DEM196");
    assertThat(command.userId()).isEqualTo(3l);
  }
}
