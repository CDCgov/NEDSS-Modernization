package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.springframework.data.domain.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageRuleServiceImplTest {
    @InjectMocks
    private PageRuleServiceImpl pageRuleServiceImpl;

    @Mock
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Mock
    private RuleCreatedEventProducer.EnabledProducer ruleCreatedEventProducer;

    @InjectMocks
    private PageRuleFinderServiceImpl pageRuleFinderServiceImpl;

    @BeforeEach
    void setup() {
        Mockito.reset(waRuleMetaDataRepository);
        Mockito.reset(ruleCreatedEventProducer);
    }

    @Test
    void should_save_ruleRequest_details_to_DB() throws RuleException {

        CreateRuleRequest ruleRequest = RuleRequestMother.ruleRequest();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());



    }

    @Test
    void should_send_ruleRequest_Event() throws RuleException {
        Long userId = 99L;
        CreateRuleRequest ruleRequest = RuleRequestMother.ruleRequest();
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);
        ArgumentCaptor<RuleCreatedEvent> eventCaptor = ArgumentCaptor.forClass(RuleCreatedEvent.class);
        Mockito.verify(ruleCreatedEventProducer, times(1)).send(eventCaptor.capture());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDateCompare() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.dateCompareRuleRequest();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDisable() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.DisableRuleRequest();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDisableIfAnySourceIsTruw() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.DisableRuleTestDataAnySourceIsTrue();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForEnableIfAnySourceIsTrue() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleTestDataAnySourceIsTrue();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForEnable() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleRequest();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForHide() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleRequest();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test()
    void shouldGiveRuleExpressionInACorrectFormatForHideIfAnySourceIsTrue() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleTestDataAnySourceIsTrue();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIfAnySourceIsTrue() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestDataAnySourceIsTrue();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }


    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIfElsePart() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData_othercomparator();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }



    @Test
    void shouldGiveRuleExpressionInACorrectFormatForUnhide() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequest();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIf() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldThrowAnExceptionIfThereIsSomethingWrongInFunctionName() {
        CreateRuleRequest ruleRequest = RuleRequestMother.InvalidRuleRequest();
        Long userId = 99L;
        RuleException exception =
                assertThrows(RuleException.class, () -> pageRuleServiceImpl.createPageRule(userId, ruleRequest));

        assertEquals(
                "gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException: Error in Creating Rule Expression and Error Message Text",
                exception.toString());
    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForUnhideIfAnySourceIsTrue() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequestIfAnySource();
        Long userId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldDeleteTheRuleID() {
        Long ruleId = 99L;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.deletePageRule(ruleId);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).deleteById(ruleId);
        assertEquals("Rule Successfully Deleted", ruleResponse.message());
    }

    @Test
    void shouldReturnNotFoundWhenRuleIdIsNotThere() throws RuleException {
        Long ruleId = 99L;
        Long userId = 99L;
        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData();
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        ruleMetadata.setId(99L);
        ruleMetadata.setRuleCd("Hide");
        ruleMetadata.setRuleExpression("testRuleExpression");
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.updatePageRule(ruleId, ruleRequest, userId);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).existsById(ruleId);
        assertEquals("RuleId Not Found", ruleResponse.message());
    }

    @Test
    void shouldUpdateRule() throws RuleException {
        Long ruleId = 99L;
        Long userId = 99L;
        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData();
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        ruleMetadata.setId(99L);
        ruleMetadata.setRuleCd("Hide");
        ruleMetadata.setRuleExpression("testRuleExpression");
        Mockito.when(waRuleMetaDataRepository.existsById(ruleId)).thenReturn(true);
        Mockito.when(waRuleMetaDataRepository.getReferenceById(ruleId)).thenReturn(ruleMetadata);
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.updatePageRule(ruleId, ruleRequest, userId);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).existsById(ruleId);
        assertEquals("Rule Successfully Updated", ruleResponse.message());
    }

    @Test
    void shouldGiveTheDetailsOfRule() {
        Long ruleId = 99L;
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        ruleMetadata.setId(99L);
        ruleMetadata.setRuleCd("Hide");
        ruleMetadata.setRuleExpression("testRuleExpression");
        ruleMetadata.setErrormsgText("testErrorMsg");
        ruleMetadata.setLogic(">=");
        ruleMetadata.setSourceValues("test123,test345");
        ruleMetadata.setTargetType("Question");
        ruleMetadata.setTargetQuestionIdentifier("test456,test789");
        Mockito.when(waRuleMetaDataRepository.getReferenceById(ruleId)).thenReturn(ruleMetadata);
        ViewRuleResponse ruleresponse = pageRuleFinderServiceImpl.getRuleResponse(ruleId);
        assertNotNull(ruleresponse);
    }

    @Test
    void shouldGiveTheDetailsOfRuleEvenSourceAndTargetValuesAreNull() {
        Long ruleId = 99L;
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        ruleMetadata.setId(99L);
        ruleMetadata.setRuleCd("Hide");
        ruleMetadata.setRuleExpression("testRuleExpression");
        ruleMetadata.setErrormsgText("testErrorMsg");
        ruleMetadata.setLogic(">=");
        ruleMetadata.setSourceValues(null);
        ruleMetadata.setTargetType("Question");
        ruleMetadata.setTargetQuestionIdentifier(null);
        Mockito.when(waRuleMetaDataRepository.getReferenceById(ruleId)).thenReturn(ruleMetadata);
        ViewRuleResponse ruleresponse = pageRuleFinderServiceImpl.getRuleResponse(ruleId);
        assertNotNull(ruleresponse);
    }

    @Test
    void shouldCreateJsForDateCompareFunction() {
        CreateRuleRequest ruleRequest = RuleRequestMother.dateCompareRuleRequest();
        SourceValuesHelper sourceValuesHelper =
                new SourceValuesHelper("INV132", "INV132", "Admission Date (INV132)", "INV132");
        List<String> targetTextList = new ArrayList<>();
        targetTextList.add("Discharge Date");
        targetTextList.add("Admission Date");
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        TargetValuesHelper targetValuesHelper = new TargetValuesHelper("INV133", targetTextList);
        JSFunctionNameHelper jsFunctionNameHelper =
                pageRuleServiceImpl.jsForDateCompare(ruleRequest, sourceValuesHelper, targetValuesHelper, ruleMetadata);
        assertNotNull(jsFunctionNameHelper);

        String jsonString = "function ruleDCompINV132null() {\n" +
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
        SourceValuesHelper sourceValuesHelper = new SourceValuesHelper("D", "Days", "Age at Onset Units", "INV144");
        List<String> targetTextList = new ArrayList<>();
        targetTextList.add("Additional Gender");
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        JSFunctionNameHelper jsFunctionNameHelper =
                pageRuleServiceImpl.jsForHideAndUnhide(ruleRequest, sourceValuesHelper, ruleMetadata);
        assertNotNull(jsFunctionNameHelper);
        String jsonString = "function ruleHideUnhINV144null()\n" +
                "{\n" +
                " var foo = [];\n" +
                "$j('#INV144 :selected').each(function(i, selected){\n" +
                " foo[i] = $j(selected).val();\n" +
                " });\n" +
                "if(foo=='' && $j('#INV144').html()!=null){foo[0]=$j('#INV144').html().replace(/^\\s+|\\s+$/g,'');}\n" +
                " if(($j.inArray('D',foo) > -1) || ($j.inArray('Days'.replace(/^\\s+|\\s+$/g,''),foo) > -1 || indexOfArray(foo,'Days')==true)){\n"
                +
                "pgHideElement('NBS213');\n" +
                " } else { \n" +
                "pgUnhideElement('NBS213');\n" +
                " }\n" +
                " var foo_2 = [];\n" +
                "$j('#INV144_2 :selected').each(function(i, selected){\n" +
                " foo_2[i] = $j(selected).val();\n" +
                " });\n" +
                "if(foo_2=='' && $j('#INV144_2').html()!=null){foo_2[0]=$j('#INV144_2').html().replace(/^\\s+|\\s+$/g,'');}\n"
                +
                " if(($j.inArray('D',foo_2) > -1) || ($j.inArray('Days'.replace(/^\\s+|\\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Days')==true)){\n"
                +
                "pgHideElement('NBS213_2');\n" +
                " } else { \n" +
                "pgUnhideElement('NBS213_2');\n" +
                " }   \n" +
                "}";

        Assert.assertEquals(jsonString, jsFunctionNameHelper.jsFunction());
    }

    @Test
    void shouldCreateJsForRequireIfFunction() {
        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData();
        SourceValuesHelper sourceValuesHelper =
                new SourceValuesHelper(null, null,
                        "Relationship with Patient/Other infected Patient?", "CON141");
        List<String> targetTextList = new ArrayList<>();
        targetTextList.add("Named");
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        TargetValuesHelper targetValuesHelper = new TargetValuesHelper("CON143", targetTextList);
        JSFunctionNameHelper jsFunctionNameHelper = pageRuleServiceImpl.requireIfJsFunction(ruleRequest,
                sourceValuesHelper, ruleMetadata, targetValuesHelper);
        assertNotNull(jsFunctionNameHelper);
        String jsonString = "function ruleRequireIfCON141null()\n" +
                "{\n" +
                " var foo = [];\n" +
                "$j('#CON141 :selected').each(function(i, selected){\n" +
                " foo[i] = $j(selected).val();\n" +
                " });\n" +
                "if(foo.length>0 && foo[0] != '') {\n" +
                "pgRequireElement('CON143');\n" +
                " } else { \n" +
                "pgRequireNotElement('CON143');\n" +
                " }   \n" +
                "}";
        Assert.assertEquals(jsonString, jsFunctionNameHelper.jsFunction());
    }

    @Test
    void shouldCreateJsForEnableAndDisableFunction() {
        CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleRequest();
        SourceValuesHelper sourceValuesHelper =
                new SourceValuesHelper("Yes", "YES", "Is the patient pregnant?", "INV178");
        List<String> targetTextList = new ArrayList<>();
        targetTextList.add("Weeks");
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        JSFunctionNameHelper jsFunctionNameHelper =
                pageRuleServiceImpl.jsForEnableAndDisable(ruleRequest, sourceValuesHelper, ruleMetadata);
        assertNotNull(jsFunctionNameHelper);

        String jsonString = "function ruleEnDisINV178null()\n" +
                "{\n" +
                " var foo = [];\n" +
                "$j('#INV178 :selected').each(function(i, selected){\n" +
                " foo[i] = $j(selected).val();\n" +
                " });\n" +
                "\n" +
                " if(($j.inArray('Yes',foo) > -1) || ($j.inArray('Yes'.replace(/^\\s+|\\s+$/g,''),foo) > -1)){\n" +
                "pgEnableElement('NBS128');\n" +
                " } else { \n" +
                "pgDisableElement('NBS128');\n" +
                " }\n" +
                "}";
        Assert.assertEquals(jsonString, jsFunctionNameHelper.jsFunction());
    }

    @Test
    void getAllPageRuleTest() {
        Pageable pageable= null;
        Page<WaRuleMetadata> ruleMetadataPage= new PageImpl<>(Collections.singletonList(morbWaRuleMetadata()));
        Mockito.when(waRuleMetaDataRepository.findAll(pageable)).thenReturn(ruleMetadataPage);
        Page<ViewRuleResponse> ruleResponse = pageRuleFinderServiceImpl.getAllPageRule(pageable);
        assertNotNull(ruleResponse);
        List<ViewRuleResponse> actualResponse = ruleResponse.stream().toList();
        assertNotNull(actualResponse);
        assertEquals(1,actualResponse.size());
        assertEquals(99,actualResponse.get(0).ruleId());
        assertEquals("Question",actualResponse.get(0).targetType());

        WaRuleMetadata data = morbWaRuleMetadata();
        data.setSourceValues("Test Source values");
        data.setTargetQuestionIdentifier("Test target questions identifier");
        ruleMetadataPage= new PageImpl<>(Collections.singletonList(data));
        Mockito.when(waRuleMetaDataRepository.findAll(pageable)).thenReturn(ruleMetadataPage);
        ruleResponse = pageRuleFinderServiceImpl.getAllPageRule(pageable);
        assertNotNull(ruleResponse);
        actualResponse = ruleResponse.stream().toList();
        assertNotNull(actualResponse);
        assertEquals(1,actualResponse.size());
        assertEquals(99,actualResponse.get(0).ruleId());
    }

    private WaRuleMetadata morbWaRuleMetadata() {
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        ruleMetadata.setId(99L);
        ruleMetadata.setRuleCd("Hide");
        ruleMetadata.setRuleExpression("testRuleExpression");
        ruleMetadata.setErrormsgText("testErrorMsg");
        ruleMetadata.setLogic(">=");
        ruleMetadata.setSourceValues(null);
        ruleMetadata.setTargetType("Question");
        ruleMetadata.setTargetQuestionIdentifier(null);
        return ruleMetadata;
    }

    @Test
    void findPageRuleTest() {
        int page = 0;
        int size =1;
        String sort ="id";
        Pageable pageRequest = PageRequest.of(page,size, Sort.by(sort));
        SearchPageRuleRequest request = new SearchPageRuleRequest("searchValue");
        Page<WaRuleMetadata> ruleMetadataPage= new PageImpl<>(Collections.singletonList(morbWaRuleMetadata()));
        Mockito.when(waRuleMetaDataRepository.
                findAllBySourceValuesContainingIgnoreCaseOrTargetQuestionIdentifierContainingIgnoreCase
                        (request.searchValue(),request.searchValue(),pageRequest)).thenReturn(ruleMetadataPage);
        Page<ViewRuleResponse> ruleResponse = pageRuleFinderServiceImpl.findPageRule(request,pageRequest);
        assertNotNull(ruleResponse);
    }

}
