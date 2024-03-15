package gov.cdc.nbs.questionbank.pagerules;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;

class EnableDisableCreatorTest {

  EnableDisableCreator creator = new EnableDisableCreator();

  @Test
  void function_name() {
    String expected = "ruleEnDisINV14411";
    String actual = creator.createJavascriptName(
        "INV144",
        11);
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
    String actual = creator.createSourceValues(
        false,
        Arrays.asList(
            new SourceValue("id", "text"),
            new SourceValue("id2", "text2")));
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void error_message() {
    String expected = "Age at Onset Units > must be ( Days , Hours ) Comments";
    String actual = creator.createErrorMessage(
        "Age at Onset Units",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        Arrays.asList("Comments"),
        ">");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_equal_enable() {
    String expected = "INV144 ( D , H ) = ^ E ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        "INV515",
        "=",
        true);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_equal_disable() {
    String expected = "INV144 ( D , H ) = ^ D ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        "INV515",
        "=",
        false);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_not_equal_disable() {
    String expected = "INV144 ( D , H ) <> ^ D ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        "INV515",
        "<>",
        false);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_not_equal_enable() {
    String expected = "INV144 ( D , H ) <> ^ E ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        "INV515",
        "<>",
        true);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_any_source() {
    String expected = "INV144 (  )  ^ E ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        true,
        "INV515",
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
    String functionName = creator.createJavascriptName(
        "INV144",
        11);
    String actual = creator.createJavascript(
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
    String functionName = creator.createJavascriptName(
        "INV144",
        11);
    String actual = creator.createJavascript(
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
    String functionName = creator.createJavascriptName(
        "INV144",
        11);
    String actual = creator.createJavascript(
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
    String functionName = creator.createJavascriptName(
        "INV144",
        11);
    String actual = creator.createJavascript(
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
    String functionName = creator.createJavascriptName(
        "INV144",
        11);
    String actual = creator.createJavascript(
        functionName,
        "INV144",
        false,
        Arrays.asList("NBS_UI_29", "NBS_INV_GENV2_UI_5", "NBS_UI_2"),
        Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
        "=",
        TargetType.SUBSECTION);
    assertThat(actual).isEqualTo(expected);
  }

}
