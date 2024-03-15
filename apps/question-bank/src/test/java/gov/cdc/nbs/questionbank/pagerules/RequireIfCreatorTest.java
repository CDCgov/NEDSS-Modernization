package gov.cdc.nbs.questionbank.pagerules;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;

class RequireIfCreatorTest {

  private final RequireIfCreator creator = new RequireIfCreator();

  @Test
  void function_name() {
    String expected = "ruleRequireIfINV14411";
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
    String expected = "INV144 ( D , H ) = ^ R ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        "INV515",
        "=");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_equal_disable() {
    String expected = "INV144 ( D , H ) = ^ R ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        "INV515",
        "=");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_not_equal_disable() {
    String expected = "INV144 ( D , H ) <> ^ R ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        "INV515",
        "<>");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_not_equal_enable() {
    String expected = "INV144 ( D , H ) <> ^ R ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        false,
        "INV515",
        "<>");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_any_source() {
    String expected = "INV144 (  )  ^ R ( INV515 )";
    String actual = creator.createExpression(
        "INV144",
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours")),
        true,
        "INV515",
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
        Arrays.asList("NBS102", "INV132"),
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
        Arrays.asList("NBS102", "INV132"),
        Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
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
        Arrays.asList("NBS102", "INV132"),
        Arrays.asList(new SourceValue("D", "Days"), new SourceValue("H", "Hours")),
        "<>");

    assertThat(actual).isEqualTo(expected);
  }

}
