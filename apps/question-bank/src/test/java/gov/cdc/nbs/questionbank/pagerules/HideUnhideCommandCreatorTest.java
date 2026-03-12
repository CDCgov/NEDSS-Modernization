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

class HideUnhideCommandCreatorTest {

  private final HideUnhideCommandCreator creator = new HideUnhideCommandCreator();

  @Test
  void function_name() {
    String expected = "ruleHideUnhINV14411()";
    String actual = creator.createJavascriptName("INV144", 11);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void error_message() {
    String expected =
        "Preceding COVID-like illness (NBS707) = must be ( Yes ) Date of COVID-like illness symptom onset";
    String actual =
        creator.createErrorMessage(
            "Preceding COVID-like illness",
            "NBS707",
            Arrays.asList(new SourceValue("1", "Yes")),
            false,
            Arrays.asList("Date of COVID-like illness symptom onset"),
            "=");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void error_message_any_source() {
    String expected =
        "Preceding COVID-like illness = must be ( Any Source Value ) Date of COVID-like illness symptom onset";
    String actual =
        creator.createErrorMessage(
            "Preceding COVID-like illness",
            "NBS707",
            Arrays.asList(new SourceValue("1", "Yes")),
            true,
            Arrays.asList("Date of COVID-like illness symptom onset"),
            "=");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void source_values() {
    String expected = "text1, text2";
    String actual =
        creator.createSourceValues(
            false, Arrays.asList(new SourceValue("id1", "text1"), new SourceValue("id2", "text2")));
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void any_source_values() {
    String expected = "Any Source Value";
    String actual =
        creator.createSourceValues(
            true, Arrays.asList(new SourceValue("id1", "text1"), new SourceValue("id2", "text2")));
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void javascript_all_source_hide_equals() {
    String expected =
        """
            function ruleHideUnhINV14414()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo=='' && $j('#INV144').html()!=null){foo[0]=$j('#INV144').html().replace(/^\s+|\s+$/g,'');}if(foo.length>0 && foo[0] != '') {
            pgHideElement('INV163');
             } else {
            pgUnhideElement('INV163');
             }
             var foo_2 = [];
            $j('#INV144_2 :selected').each(function(i, selected){
             foo_2[i] = $j(selected).val();
             });
            if(foo_2=='' && $j('#INV144_2').html()!=null){foo_2[0]=$j('#INV144_2').html().replace(/^\s+|\s+$/g,'');}if(foo_2.length>0 && foo_2[0] != '') {
            pgHideElement('INV163_2');
             } else {
            pgUnhideElement('INV163_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV144", 14);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.HIDE,
            "description",
            "INV144",
            true,
            Arrays.asList(),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV163"),
            "",
            Arrays.asList());
    String actual = creator.createJavascript(functionName, request);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void javascript_all_source_hide_not_equals() {
    String expected =
        """
            function ruleHideUnhINV15014()
            {
             var foo = [];
            $j('#INV150 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo=='' && $j('#INV150').html()!=null){foo[0]=$j('#INV150').html().replace(/^\s+|\s+$/g,'');}if(foo.length>0 && foo[0] != '') {
            pgHideElement('INV163');
             } else {
            pgUnhideElement('INV163');
             }
             var foo_2 = [];
            $j('#INV150_2 :selected').each(function(i, selected){
             foo_2[i] = $j(selected).val();
             });
            if(foo_2=='' && $j('#INV150_2').html()!=null){foo_2[0]=$j('#INV150_2').html().replace(/^\s+|\s+$/g,'');}if(foo_2.length>0 && foo_2[0] != '') {
            pgHideElement('INV163_2');
             } else {
            pgUnhideElement('INV163_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV150", 14);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.HIDE,
            "description",
            "INV150",
            true,
            Arrays.asList(),
            Comparator.NOT_EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV163"),
            "",
            Arrays.asList());
    String actual = creator.createJavascript(functionName, request);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void javascript_all_source_unhide() {
    String expected =
        """
            function ruleHideUnhINV14414()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo=='' && $j('#INV144').html()!=null){foo[0]=$j('#INV144').html().replace(/^\s+|\s+$/g,'');}if(foo.length>0 && foo[0] != '') {
            pgUnhideElement('INV163');
             } else {
            pgHideElement('INV163');
             }
             var foo_2 = [];
            $j('#INV144_2 :selected').each(function(i, selected){
             foo_2[i] = $j(selected).val();
             });
            if(foo_2=='' && $j('#INV144_2').html()!=null){foo_2[0]=$j('#INV144_2').html().replace(/^\s+|\s+$/g,'');}if(foo_2.length>0 && foo_2[0] != '') {
            pgUnhideElement('INV163_2');
             } else {
            pgHideElement('INV163_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV144", 14);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.UNHIDE,
            "description",
            "INV144",
            true,
            Arrays.asList(),
            Comparator.NOT_EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV163"),
            "",
            Arrays.asList());
    String actual = creator.createJavascript(functionName, request);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void unhide_specific_equals() {
    String expected =
        """
            function ruleHideUnhINV14414()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo=='' && $j('#INV144').html()!=null){foo[0]=$j('#INV144').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Days')==true)){
            pgUnhideElement('INV163');
             } else {
            pgHideElement('INV163');
             }
             var foo_2 = [];
            $j('#INV144_2 :selected').each(function(i, selected){
             foo_2[i] = $j(selected).val();
             });
            if(foo_2=='' && $j('#INV144_2').html()!=null){foo_2[0]=$j('#INV144_2').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo_2) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Days')==true)){
            pgUnhideElement('INV163_2');
             } else {
            pgHideElement('INV163_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV144", 14);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.UNHIDE,
            "description",
            "INV144",
            false,
            Arrays.asList(new SourceValue("D", "Days")),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV163"),
            "",
            Arrays.asList());
    String actual = creator.createJavascript(functionName, request);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void unhide_specific_not_equals() {
    String expected =
        """
            function ruleHideUnhINV14414()
            {
             var foo = [];
            $j('#INV144 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo=='' && $j('#INV144').html()!=null){foo[0]=$j('#INV144').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Days')==true)){
            pgHideElement('INV163');
             } else {
            pgUnhideElement('INV163');
             }
             var foo_2 = [];
            $j('#INV144_2 :selected').each(function(i, selected){
             foo_2[i] = $j(selected).val();
             });
            if(foo_2=='' && $j('#INV144_2').html()!=null){foo_2[0]=$j('#INV144_2').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo_2) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Days')==true)){
            pgHideElement('INV163_2');
             } else {
            pgUnhideElement('INV163_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV144", 14);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.UNHIDE,
            "description",
            "INV144",
            false,
            Arrays.asList(new SourceValue("D", "Days")),
            Comparator.NOT_EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("INV163"),
            "",
            Arrays.asList());
    String actual = creator.createJavascript(functionName, request);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void hide_specific_not_equals() {
    String expected =
        """
            function ruleHideUnhINV15420()
            {
             var foo = [];
            $j('#INV154 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo=='' && $j('#INV154').html()!=null){foo[0]=$j('#INV154').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Days')==true) || ($j.inArray('H',foo) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Hours')==true) || ($j.inArray('N',foo) > -1) || ($j.inArray('Minutes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Minutes')==true)){
            pgUnhideElement('DEM161');
            pgUnhideElement('DEM196');
             } else {
            pgHideElement('DEM161');
            pgHideElement('DEM196');
             }
             var foo_2 = [];
            $j('#INV154_2 :selected').each(function(i, selected){
             foo_2[i] = $j(selected).val();
             });
            if(foo_2=='' && $j('#INV154_2').html()!=null){foo_2[0]=$j('#INV154_2').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo_2) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Days')==true) || ($j.inArray('H',foo_2) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Hours')==true) || ($j.inArray('N',foo_2) > -1) || ($j.inArray('Minutes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Minutes')==true)){
            pgUnhideElement('DEM161_2');
            pgUnhideElement('DEM196_2');
             } else {
            pgHideElement('DEM161_2');
            pgHideElement('DEM196_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV154", 20);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.HIDE,
            "description",
            "INV154",
            false,
            Arrays.asList(
                new SourceValue("D", "Days"),
                new SourceValue("H", "Hours"),
                new SourceValue("N", "Minutes")),
            Comparator.NOT_EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList("DEM161", "DEM196"),
            "",
            Arrays.asList());
    String actual = creator.createJavascript(functionName, request);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void hide_specific_equals() {
    String expected =
        """
            function ruleHideUnhINV15420()
            {
             var foo = [];
            $j('#INV154 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo=='' && $j('#INV154').html()!=null){foo[0]=$j('#INV154').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Days')==true) || ($j.inArray('H',foo) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Hours')==true) || ($j.inArray('N',foo) > -1) || ($j.inArray('Minutes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Minutes')==true)){
            pgHideElement('DEM161');
            pgHideElement('DEM196');
             } else {
            pgUnhideElement('DEM161');
            pgUnhideElement('DEM196');
             }
             var foo_2 = [];
            $j('#INV154_2 :selected').each(function(i, selected){
             foo_2[i] = $j(selected).val();
             });
            if(foo_2=='' && $j('#INV154_2').html()!=null){foo_2[0]=$j('#INV154_2').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo_2) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Days')==true) || ($j.inArray('H',foo_2) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Hours')==true) || ($j.inArray('N',foo_2) > -1) || ($j.inArray('Minutes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Minutes')==true)){
            pgHideElement('DEM161_2');
            pgHideElement('DEM196_2');
             } else {
            pgUnhideElement('DEM161_2');
            pgUnhideElement('DEM196_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV154", 20);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.HIDE,
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
            "",
            Arrays.asList());
    String actual = creator.createJavascript(functionName, request);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void hide_specific_equals_subsection() {
    String expected =
        """
            function ruleHideUnhINV15420()
            {
             var foo = [];
            $j('#INV154 :selected').each(function(i, selected){
             foo[i] = $j(selected).val();
             });
            if(foo=='' && $j('#INV154').html()!=null){foo[0]=$j('#INV154').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Days')==true) || ($j.inArray('H',foo) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Hours')==true) || ($j.inArray('N',foo) > -1) || ($j.inArray('Minutes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Minutes')==true)){
            pgSubSectionHidden('DEM161');
            pgSubSectionHidden('DEM196');
             } else {
            pgSubSectionShown('DEM161');
            pgSubSectionShown('DEM196');
             }
             var foo_2 = [];
            $j('#INV154_2 :selected').each(function(i, selected){
             foo_2[i] = $j(selected).val();
             });
            if(foo_2=='' && $j('#INV154_2').html()!=null){foo_2[0]=$j('#INV154_2').html().replace(/^\s+|\s+$/g,'');}
             if(($j.inArray('D',foo_2) > -1) || ($j.inArray('Days'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Days')==true) || ($j.inArray('H',foo_2) > -1) || ($j.inArray('Hours'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Hours')==true) || ($j.inArray('N',foo_2) > -1) || ($j.inArray('Minutes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Minutes')==true)){
            pgSubSectionHidden('DEM161_2');
            pgSubSectionHidden('DEM196_2');
             } else {
            pgSubSectionShown('DEM161_2');
            pgSubSectionShown('DEM196_2');
             }
            }
              """;
    String functionName = creator.createJavascriptName("INV154", 20);
    RuleRequest request =
        new RuleRequest(
            RuleFunction.HIDE,
            "description",
            "INV154",
            false,
            Arrays.asList(
                new SourceValue("D", "Days"),
                new SourceValue("H", "Hours"),
                new SourceValue("N", "Minutes")),
            Comparator.EQUAL_TO,
            TargetType.SUBSECTION,
            Arrays.asList("DEM161", "DEM196"),
            "",
            Arrays.asList());
    String actual = creator.createJavascript(functionName, request);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void creates_command() {
    RuleRequest request =
        new RuleRequest(
            RuleFunction.HIDE,
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
    assertThat(command.ruleFunction()).isEqualTo("Hide");
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
            RuleFunction.HIDE,
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
    assertThat(command.userId()).isEqualTo(3l);
  }

  @Test
  void expression_unhide() {
    String expected = "INV144 (  )  ^ S ( DEM161 )";
    String actual =
        creator.createExpression("INV144", null, true, Arrays.asList("DEM161"), "=", false);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_hide() {
    String expected = "INV144 (  )  ^ H ( DEM161 )";
    String actual =
        creator.createExpression("INV144", null, true, Arrays.asList("DEM161"), "=", true);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void expression_hide_multi() {
    String expected = "INV144 (  )  ^ H ( DEM161 , DEM162 )";
    String actual =
        creator.createExpression(
            "INV144", null, true, Arrays.asList("DEM161", "DEM162"), "=", true);
    assertThat(actual).isEqualTo(expected);
  }
}
