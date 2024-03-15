package gov.cdc.nbs.questionbank.pagerules;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;

class HideUnhideCreatorTest {

  private final HideUnhideCreator creator = new HideUnhideCreator();


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
    String actual = creator.createJavascript(
        functionName,
        "INV144",
        true,
        Arrays.asList("INV163"),
        null,
        "=",
        true);
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
    String actual = creator.createJavascript(
        functionName,
        "INV150",
        true,
        Arrays.asList("INV163"),
        null,
        "<>",
        true);
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
    String actual = creator.createJavascript(
        functionName,
        "INV144",
        true,
        Arrays.asList("INV163"),
        null,
        "=",
        false);
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
    String actual = creator.createJavascript(
        functionName,
        "INV144",
        false,
        Arrays.asList("INV163"),
        Arrays.asList(new SourceValue("D", "Days")),
        "=",
        false);
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
    String actual = creator.createJavascript(
        functionName,
        "INV144",
        false,
        Arrays.asList("INV163"),
        Arrays.asList(new SourceValue("D", "Days")),
        "<>",
        false);
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
    String actual = creator.createJavascript(
        functionName,
        "INV154",
        false,
        Arrays.asList("DEM161", "DEM196"),
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours"),
            new SourceValue("N", "Minutes")),
        "<>",
        true);
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
    String actual = creator.createJavascript(
        functionName,
        "INV154",
        false,
        Arrays.asList("DEM161", "DEM196"),
        Arrays.asList(
            new SourceValue("D", "Days"),
            new SourceValue("H", "Hours"),
            new SourceValue("N", "Minutes")),
        "=",
        true);
    assertThat(actual).isEqualTo(expected);
  }
}
