package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import org.springframework.data.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PageRuleServiceImplTest {
  //  @InjectMocks
  //  private PageRuleServiceImpl pageRuleServiceImpl;

  @Mock
  private WaRuleMetaDataRepository waRuleMetaDataRepository;

  @Mock
  private RuleResponseMapper ruleResponseMapper;

  @InjectMocks
  private PageRuleFinderServiceImpl pageRuleFinderServiceImpl;

  @BeforeEach
  void setup() {
    Mockito.reset(waRuleMetaDataRepository);
  }



  // @Test
  // void shouldReturnNotFoundWhenRuleIdIsNotThere() throws RuleException {
  //     Long ruleId = 99L;
  //     Long userId = 99L;
  //     CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData();
  //     WaRuleMetadata ruleMetadata = new WaRuleMetadata();
  //     ruleMetadata.setId(99L);
  //     ruleMetadata.setRuleCd("Hide");
  //     ruleMetadata.setRuleExpression("testRuleExpression");
  //     CreateRuleResponse ruleResponse = pageRuleServiceImpl.updatePageRule(ruleId, ruleRequest, userId, 123456L);
  //     Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).existsById(ruleId);
  //     assertEquals("RuleId Not Found", ruleResponse.message());
  // }

  // @Test
  // void shouldUpdateRule() throws RuleException {
  //     Long ruleId = 99L;
  //     Long userId = 99L;
  //     CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData();
  //     WaRuleMetadata ruleMetadata = new WaRuleMetadata();
  //     ruleMetadata.setId(99L);
  //     ruleMetadata.setRuleCd("Hide");
  //     ruleMetadata.setRuleExpression("testRuleExpression");
  //     Mockito.when(waRuleMetaDataRepository.existsById(ruleId)).thenReturn(true);
  //     Mockito.when(waRuleMetaDataRepository.getReferenceById(ruleId)).thenReturn(ruleMetadata);
  //     CreateRuleResponse ruleResponse = pageRuleServiceImpl.updatePageRule(ruleId, ruleRequest, userId, 123456L);
  //     Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).existsById(ruleId);
  //     assertEquals("Rule Successfully Updated", ruleResponse.message());
  // }



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
    Mockito.when(ruleResponseMapper.mapRuleResponse(any(WaRuleMetadata.class))).thenReturn(getViewRuleResponse());
    ViewRuleResponse ruleresponse = pageRuleFinderServiceImpl.getRuleResponse(ruleId);
    assertNotNull(ruleresponse);
    assertEquals(2, ruleresponse.targetQuestions().size());
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
    Mockito.when(ruleResponseMapper.mapRuleResponse(any(WaRuleMetadata.class))).thenReturn(getViewRuleResponse());
    ViewRuleResponse ruleresponse = pageRuleFinderServiceImpl.getRuleResponse(ruleId);
    assertNotNull(ruleresponse);
  }



  @Test
  void getAllPageRuleTest() {
    Pageable pageable = null;
    Page<WaRuleMetadata> ruleMetadataPage = new PageImpl<>(Collections.singletonList(morbWaRuleMetadata()));
    Mockito.when(waRuleMetaDataRepository.findByWaTemplateUid(123456L, pageable)).thenReturn(ruleMetadataPage);
    Mockito.when(ruleResponseMapper.mapRuleResponse(any(Page.class))).thenReturn(getViewRuleResponsePage());
    Page<ViewRuleResponse> ruleResponse = pageRuleFinderServiceImpl.getAllPageRule(pageable, 123456L);
    assertNotNull(ruleResponse);
    List<ViewRuleResponse> actualResponse = ruleResponse.stream().toList();
    assertNotNull(actualResponse);
    assertEquals(1, actualResponse.size());
    assertEquals(99, actualResponse.get(0).ruleId());
    assertEquals("Question", actualResponse.get(0).targetType());

    WaRuleMetadata data = morbWaRuleMetadata();
    data.setSourceValues("Test Source values");
    data.setTargetQuestionIdentifier("Test target questions identifier");
    ruleMetadataPage = new PageImpl<>(Collections.singletonList(data));
    Mockito.when(waRuleMetaDataRepository.findByWaTemplateUid(123456L, pageable)).thenReturn(ruleMetadataPage);
    Mockito.when(ruleResponseMapper.mapRuleResponse(any(Page.class))).thenReturn(getViewRuleResponsePage());
    ruleResponse = pageRuleFinderServiceImpl.getAllPageRule(pageable, 123456L);
    assertNotNull(ruleResponse);
    actualResponse = ruleResponse.stream().toList();
    assertNotNull(actualResponse);
    assertEquals(1, actualResponse.size());
    assertEquals(99, actualResponse.get(0).ruleId());
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
    int size = 1;
    String sort = "id";
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
    SearchPageRuleRequest request = new SearchPageRuleRequest("searchValue");
    Page<WaRuleMetadata> ruleMetadataPage = new PageImpl<>(Collections.singletonList(morbWaRuleMetadata()));
    Mockito.when(waRuleMetaDataRepository
            .findAllBySourceValuesContainingIgnoreCaseOrTargetQuestionIdentifierContainingIgnoreCase(
                request.searchValue(), request.searchValue(), pageRequest))
        .thenReturn(ruleMetadataPage);
    Mockito.when(ruleResponseMapper.mapRuleResponse(any(Page.class))).thenReturn(getViewRuleResponsePage());
    Page<ViewRuleResponse> ruleResponse = pageRuleFinderServiceImpl.findPageRule(request, pageRequest);
    assertNotNull(ruleResponse);

    List<ViewRuleResponse> actualResponse = ruleResponse.stream().toList();
    assertNotNull(actualResponse);
    assertEquals(1, actualResponse.size());
    assertEquals(99, actualResponse.get(0).ruleId());
    assertEquals("Question", actualResponse.get(0).targetType());
  }

  private ViewRuleResponse getViewRuleResponse() {
    return new ViewRuleResponse(
        99l,
        123l,
        "ruleFunction",
        "ruleDescription",
        "sourceIdentifier",
        new ArrayList<>(),
        "comparator",
        "Question",
        "errorMsgText",
        Arrays.asList(new QuestionInfo("label1", "test456"), new QuestionInfo("label2", "test789"))
    );
  }

  Page<ViewRuleResponse> getViewRuleResponsePage() {
    List<ViewRuleResponse> viewRuleResponseList = Arrays.asList(getViewRuleResponse());
    Pageable pageable = PageRequest.of(0, 1);
    return new PageImpl<>(viewRuleResponseList, pageable, 5);

  }

}
