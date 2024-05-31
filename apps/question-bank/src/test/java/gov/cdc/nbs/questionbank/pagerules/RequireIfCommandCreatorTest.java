package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RequireIfCommandCreatorTest {

  private final RequireIfCommandCreator creator = new RequireIfCommandCreator();

  @Test
  void function_name() {
    String expected = "ruleRequireIfINV14411()";
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
        List.of(
            new SourceValue("id", "text"),
            new SourceValue("id2", "text2")));
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void error_message() {
    String expected = "Age at Onset Units <> must be ( Days, Hours ) Comments";
    String actual = creator.createErrorMessage(
        "Age at Onset Units",
        List.of(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        List.of("Comments"),
        "<>");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void error_message_any_source() {
    String expected = "Age at Onset Units <> must be ( Any Source Value ) Comments";
    String actual = creator.createErrorMessage(
        "Age at Onset Units",
        List.of(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        true,
        List.of("Comments"),
        "<>");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_equal_enable() {
    String expected = "INV144 ( D , H ) = ^ R ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        List.of(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        List.of("INV515"),
        "=");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_not_equal_disable() {
    String expected = "INV144 ( D , H ) <> ^ R ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        List.of(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        List.of("INV515"),
        "<>");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_not_equal_disable_multi() {
    String expected = "INV144 ( D , H ) <> ^ R ( INV515 , INV616 )";
    String actual = creator.createExpression(
        "INV144",
        List.of(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        List.of("INV515", "INV616"),
        "<>");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_any_source() {
    String expected = "INV144 (  )  ^ R ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        List.of(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        true,
        List.of("INV515"),
        "<>");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void javascript_any_source_value() {
    String expected = """
        function ruleRequireIfINV144999()
        {
         var foo = [];
        $j('#INV144 :selected').each(function(i, selected){
         foo[i] = $j(selected).val();
         });
        if(foo.length>0 && foo[0] != '') {
        pgRequireElement('NBS102');
        pgRequireElement('INV132');
         } else {
        pgRequireNotElement('NBS102');
        pgRequireNotElement('INV132');
         }
        }
        """;

    String functionName = creator.createJavascriptName("INV144", 999);
    String actual = creator.createJavascript(
        functionName,
        "INV144",
        true,
        List.of("NBS102", "INV132"),
        null,
        null);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void javascript_specific_source_value_equals() {
    String expected = """
        function ruleRequireIfINV144999()
        {
         var foo = [];
        $j('#INV144 :selected').each(function(i, selected){
         foo[i] = $j(selected).val();
         });
        if(foo.length==0) return;

         if(($j.inArray('D',foo) > -1) || ($j.inArray('H',foo) > -1)){
        pgRequireElement('NBS102');
        pgRequireElement('INV132');
         } else {
        pgRequireNotElement('NBS102');
        pgRequireNotElement('INV132');
         }
        }
        """;

    String functionName = creator.createJavascriptName("INV144", 999);
    String actual = creator.createJavascript(
        functionName,
        "INV144",
        false,
        List.of("NBS102", "INV132"),
        List.of(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
        "=");

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void javascript_specific_source_value_not_equals() {
    String expected = """
        function ruleRequireIfINV144999()
        {
         var foo = [];
        $j('#INV144 :selected').each(function(i, selected){
         foo[i] = $j(selected).val();
         });
        if(foo.length==0) return;

         if(($j.inArray('D',foo) > -1) || ($j.inArray('H',foo) > -1)){
        pgRequireNotElement('NBS102');
        pgRequireNotElement('INV132');
         } else {
        pgRequireElement('NBS102');
        pgRequireElement('INV132');
         }
        }
        """;

    String functionName = creator.createJavascriptName("INV144", 999);
    String actual = creator.createJavascript(
        functionName,
        "INV144",
        false,
        List.of("NBS102", "INV132"),
        List.of(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
        "<>");

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void creates_command() {
    RuleRequest request = new RuleRequest(
        RuleFunction.REQUIRE_IF,
        "description",
        "INV154",
        false,
        List.of(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours"),
            new SourceValue("N", "Minutes")),
        Comparator.EQUAL_TO,
        TargetType.SUBSECTION,
        List.of("DEM161", "DEM196"),
        "source text",
        List.of());
    PageContentCommand.AddRuleCommand command = creator.create(1887L, request, 3L, 9L);
    assertThat(command).isNotNull();
    assertThat(command.targetType()).isEqualTo("SUBSECTION");
    assertThat(command.ruleFunction()).isEqualTo("Require If");
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
        RuleFunction.REQUIRE_IF,
        "description",
        "INV154",
        false,
        List.of(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours"),
            new SourceValue("N", "Minutes")),
        Comparator.EQUAL_TO,
        TargetType.QUESTION,
        List.of("DEM161", "DEM196"),
        "source text",
        List.of());
    PageContentCommand.UpdateRuleCommand command = creator.update(1887L, request, 3L);
    assertThat(command).isNotNull();
    assertThat(command.targetType()).isEqualTo("QUESTION");
    assertThat(command.description()).isEqualTo("description");
    assertThat(command.comparator()).isEqualTo("=");
    assertThat(command.sourceIdentifier()).isEqualTo(request.sourceIdentifier());
    assertThat(command.userId()).isEqualTo(3L);
  }
}
