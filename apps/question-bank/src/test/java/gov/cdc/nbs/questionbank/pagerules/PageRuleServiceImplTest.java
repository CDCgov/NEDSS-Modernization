package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.pagerules.Rule.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageRuleServiceImplTest {
  @InjectMocks
  private PageRuleServiceImpl pageRuleServiceImpl;


  @Mock
  private WaRuleMetaDataRepository waRuleMetaDataRepository;


  @Test
  void shouldCreateJsForDateCompareFunction() {
    CreateRuleRequest ruleRequest = RuleRequestMother.dateCompareRuleRequest();
    SourceValuesHelper sourceValuesHelper =
        new SourceValuesHelper("INV132", "INV132", "Admission Date (INV132)", "INV132");
    List<String> targetTextList = new ArrayList<>();
    targetTextList.add("Discharge Date");
    targetTextList.add("Admission Date");
    TargetValuesHelper targetValuesHelper = new TargetValuesHelper("INV133", targetTextList);
    JSFunctionNameHelper jsFunctionNameHelper =
        pageRuleServiceImpl.jsForDateCompare(ruleRequest, sourceValuesHelper, targetValuesHelper, getWaRuleMetadata());
    assertNotNull(jsFunctionNameHelper);

    String jsonString = "function ruleDCompINV1321() {\n" +
        "    var i = 0;\n" +
        "    var errorElts = new Array(); \n" +
        "    var errorMsgs = new Array(); \n" +
        "\n" +
        " if ((getElementByIdOrByName(\"INV132\").value)==''){ \n" +
        " return {elements : errorElts, labels : errorMsgs}; }\n" +
        " var sourceStr =getElementByIdOrByName(\"INV132\").value;\n" +
        " var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);\n" +
        " var targetElt;\n" +
        " var targetStr = ''; \n" +
        " var targetDate = '';\n" +
        " targetStr =getElementByIdOrByName(\"INV133\") == null ? \"\" :getElementByIdOrByName(\"INV133\").value;\n"
        +
        " if (targetStr!=\"\") {\n" +
        "    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);\n" +
        " if (!(srcDate <= targetDate)) {\n" +
        " var srcDateEle=getElementByIdOrByName(\"INV132\");\n" +
        " var targetDateEle=getElementByIdOrByName(\"INV133\");\n" +
        " var srca2str=buildErrorAnchorLink(srcDateEle,\"Admission Date\");\n" +
        " var targeta2str=buildErrorAnchorLink(targetDateEle,\"Discharge Date\");\n" +
        "    errorMsgs[i]=srca2str + \" must be <= \" + targeta2str; \n" +
        "    colorElementLabelRed(srcDateEle); \n" +
        "    colorElementLabelRed(targetDateEle); \n" +
        "errorElts[i++]=getElementByIdOrByName(\"INV133\"); \n" +
        "}\n" +
        "  }\n" +
        " return {elements : errorElts, labels : errorMsgs}\n" +
        "}";

    Assert.assertEquals(jsonString, jsFunctionNameHelper.jsFunction());

  }

  @Test
  void shouldCreateJsForHideAndUnhideFunction() {
    CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleRequest();
    SourceValuesHelper sourceValuesHelper =
        new SourceValuesHelper("M", "Male", "Current Sex", "DEM113");
    JSFunctionNameHelper jsFunctionNameHelper =
        pageRuleServiceImpl.jsForHideAndUnhide(ruleRequest, sourceValuesHelper, getWaRuleMetadata());
    assertNotNull(jsFunctionNameHelper);
    String jsonString = "function ruleHideUnhDEM1131()\n" +
        "{\n" +
        " var foo = [];\n" +
        "$j('#DEM113 :selected').each(function(i, selected){\n" +
        " foo[i] = $j(selected).val();\n" +
        " });\n" +
        "if(foo=='' && $j('#DEM113').html()!=null){foo[0]=$j('#DEM113').html().replace(/^\\s+|\\s+$/g,'');}\n" +
        " if(($j.inArray('M',foo) > -1) || ($j.inArray('Male'.replace(/^\\s+|\\s+$/g,''),foo) > -1 || indexOfArray(foo,'Male')==true)){\n"
        +
        "pgHideElement('INV143');\n" +
        " } else { \n" +
        "pgUnhideElement('INV143');\n" +
        " }\n" +
        " var foo_2 = [];\n" +
        "$j('#DEM113_2 :selected').each(function(i, selected){\n" +
        " foo_2[i] = $j(selected).val();\n" +
        " });\n" +
        "if(foo_2=='' && $j('#DEM113_2').html()!=null){foo_2[0]=$j('#DEM113_2').html().replace(/^\\s+|\\s+$/g,'');}\n"
        +
        " if(($j.inArray('M',foo_2) > -1) || ($j.inArray('Male'.replace(/^\\s+|\\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Male')==true)){\n"
        +
        "pgHideElement('INV143_2');\n" +
        " } else { \n" +
        "pgUnhideElement('INV143_2');\n" +
        " }   \n" +
        "}";

    Assert.assertEquals(jsonString, jsFunctionNameHelper.jsFunction());
  }

  @Test
  void shouldCreateJsForRequireIfFunction() {
    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleRequest();
    SourceValuesHelper sourceValuesHelper =
        new SourceValuesHelper("M,F", "Male,Female", "Current Sex", "DEM113");
    List<String> targetTextList = new ArrayList<>();
    targetTextList.add("Male");
    TargetValuesHelper targetValuesHelper = new TargetValuesHelper("INV143", targetTextList);
    JSFunctionNameHelper jsFunctionNameHelper = pageRuleServiceImpl.requireIfJsFunction(ruleRequest,
        sourceValuesHelper, getWaRuleMetadata(), targetValuesHelper);
    assertNotNull(jsFunctionNameHelper);
    String jsonString = "function ruleRequireIfDEM1131()\n" +
        "{\n" +
        " var foo = [];\n" +
        "$j('#DEM113 :selected').each(function(i, selected){\n" +
        " foo[i] = $j(selected).val();\n" +
        " });\n" +
        "if(foo.length==0) return;\n" +
        "\n" +
        " if(($j.inArray('MAL',foo) > -1)){\n" +
        "pgRequireElement('INV143');\n" +
        " } else { \n" +
        "pgRequireNotElement('INV143');\n" +
        " }   \n" +
        "}";
    Assert.assertEquals(jsonString, jsFunctionNameHelper.jsFunction());
  }

  @Test
  void shouldCreateJsForRequireIfFunctionOneSourceID() {
    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleRequestOneSource();
    SourceValuesHelper sourceValuesHelper =
        new SourceValuesHelper("M", "Male", "Current Sex", "DEM113");
    List<String> targetTextList = new ArrayList<>();
    targetTextList.add("Male");
    TargetValuesHelper targetValuesHelper = new TargetValuesHelper("INV143", targetTextList);
    JSFunctionNameHelper jsFunctionNameHelper = pageRuleServiceImpl.requireIfJsFunction(ruleRequest,
        sourceValuesHelper, getWaRuleMetadata(), targetValuesHelper);
    assertNotNull(jsFunctionNameHelper);
    String jsonString = "function ruleRequireIfDEM1131()\n" +
        "{\n" +
        " var foo = [];\n" +
        "$j('#DEM113 :selected').each(function(i, selected){\n" +
        " foo[i] = $j(selected).val();\n" +
        " });\n" +
        "if(foo.length==0) return;\n" +
        "\n" +
        " if(($j.inArray('MAL',foo) > -1)){\n" +
        "pgRequireNotElement('INV143');\n" +
        " } else { \n" +
        "pgRequireElement('INV143');\n" +
        " }   \n" +
        "}";
    Assert.assertEquals(jsonString, jsFunctionNameHelper.jsFunction());
  }

  @Test
  void shouldCreateJsForEnableAndDisableFunction() {
    CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleRequest();
    SourceValuesHelper sourceValuesHelper =
        new SourceValuesHelper("M", "Male", "Current Sex", "DEM113");

    JSFunctionNameHelper jsFunctionNameHelper =
        pageRuleServiceImpl.jsForEnableAndDisable(ruleRequest, sourceValuesHelper, getWaRuleMetadata());
    assertNotNull(jsFunctionNameHelper);

    String jsonString = "function ruleEnDisDEM1131()\n" +
        "{\n" +
        " var foo = [];\n" +
        "$j('#DEM113 :selected').each(function(i, selected){\n" +
        " foo[i] = $j(selected).val();\n" +
        " });\n" +
        "\n" +
        " if(($j.inArray('M',foo) > -1) || ($j.inArray('M'.replace(/^\\s+|\\s+$/g,''),foo) > -1)){\n" +
        "pgEnableElement('INV143');\n" +
        " } else { \n" +
        "pgDisableElement('INV143');\n" +
        " }pgDisableElement('INV143');\n" +
        " else { \n" +
        "pgEnableElement('INV143');\n" +
        " }\n" +
        "}";
    Assert.assertEquals(jsonString, jsFunctionNameHelper.jsFunction());
  }

  @Test
  void should_save_ruleRequest_details_to_DB() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.ruleRequest();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("INV214 (Test Id,Test Id2) >= ^ E (test123,test234)", ruleData.ruleExpression());
    assertEquals("TestSource1,TestSource2", ruleData.sourceValues());

  }


  @Test
  void shouldGiveRuleExpressionInACorrectFormatForDateCompare() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.dateCompareRuleRequest();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("INV132 <= ^ DT ( INV133 )", ruleData.ruleExpression());
    assertEquals("Admission Date must be <= Discharge Date", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForDisable() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.DisableRuleRequest();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("INV123 (Test Id,Test Id2) <> ^ D (test123,test234)", ruleData.ruleExpression());
    assertEquals(
        "test <> must be (TestSource1,TestSource2) Admission Date,test <> must be (TestSource1,TestSource2) Discharge Date",
        ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForDisableIfAnySourceIsTruw() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.DisableRuleTestDataAnySourceIsTrue();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 ( ) ^ D ( INV143 )", ruleData.ruleExpression());
    assertEquals("Current Sex  must be ( Any Source Value ) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForEnableIfAnySourceIsTrue() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleTestDataAnySourceIsTrue();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 ( ) ^ E ( INV143 )", ruleData.ruleExpression());
    assertEquals("Current Sex  must be ( Any Source Value ) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForEnable() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleRequest();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) = ^ E (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex = must be (Male) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForEnableNotEq() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleRequestNotEq();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) <> ^ E (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex <> must be (Male) Age at Onset", ruleData.errorMsgText());

  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForHide() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleRequest();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) = ^ H (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex = must be (Male) Age at Onset", ruleData.errorMsgText());

  }

  @Test()
  void shouldGiveRuleExpressionInACorrectFormatForHideIfAnySourceIsTrue() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleTestDataAnySourceIsTrue();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 ( ) ^ H ( INV143 )", ruleData.ruleExpression());
    assertEquals("Current Sex  must be ( Any Source Value ) Age at Onset", ruleData.errorMsgText());

  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForRequireIfAnySourceIsTrue() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestDataAnySourceIsTrue();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 ( ) ^ R ( INV143 )", ruleData.ruleExpression());
    assertEquals("Current Sex   ( Any Source Value ) Age at Onset is required", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForUnhide() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequest();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) <> ^ S (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex <>must be(Male) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForUnhideEq() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequestEq();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) = ^ S (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex =must be(Male) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForUnhideAnySource() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequestIfAnySource();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("INV123 ( ) ^ S ( test123,test234 )", ruleData.ruleExpression());
    assertEquals("test  must be (( Any Source Value )) Admission Date,Discharge Date", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForUnhideAnySourceComp() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequestIfAnySourceComp();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("INV123 (null) <> ^ S (test123,test234)", ruleData.ruleExpression());
    assertEquals("test <>must be(null) Admission Date,Discharge Date", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForUnhideSubsection() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequestSubsectionType();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) = ^ S (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex =must be(Male) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForUnhideSubsectionComparator() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequestSubsectionTypeComparator();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) <> ^ S (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex <>must be(Male) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForHideSubsection() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleRequestSubsectionType();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) = ^ H (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex = must be (Male) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForHideSubsectionComparator() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleRequestSubsectionTypeComparator();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M) <> ^ H (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex <> must be (Male) Age at Onset", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForRequireIf() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleRequest();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M,F) = ^ R (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex = (Male,Female) Age at Onset is required", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForRequireIfAny() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleRequestAny();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 ( ) ^ R ( INV143 )", ruleData.ruleExpression());
    assertEquals("Current Sex   ( Any Source Value ) Age at Onset is required", ruleData.errorMsgText());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForRequireIfAnyOtherComp() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleRequestAnyOtherComp();
    RuleData ruleData = pageRuleServiceImpl.createRuleData(ruleRequest, getWaRuleMetadata());
    assertEquals("DEM113 (M,F) <> ^ R (INV143)", ruleData.ruleExpression());
    assertEquals("Current Sex <> (Male,Female) Age at Onset is required", ruleData.errorMsgText());
  }

  @Test
  void testUpdatePageRule() {
    Long ruleId = 1L;
    CreateRuleRequest request = RuleRequestMother.RequireIfRuleRequest();
    Long userId = 123L;
    Long page = 456L;

    WaRuleMetadata waRuleMetadata = new WaRuleMetadata();
    waRuleMetadata.setId(ruleId);
    when(waRuleMetaDataRepository.existsById(ruleId)).thenReturn(true);
    when(waRuleMetaDataRepository.getReferenceById(ruleId)).thenReturn(waRuleMetadata);

    CreateRuleResponse response = pageRuleServiceImpl.updatePageRule(ruleId, request, userId, page);
    assertNotNull(response);
  }

  private WaRuleMetadata getWaRuleMetadata() {
    WaRuleMetadata waRuleMetadata = new WaRuleMetadata();
    waRuleMetadata.setId(1l);
    return waRuleMetadata;
  }
}
