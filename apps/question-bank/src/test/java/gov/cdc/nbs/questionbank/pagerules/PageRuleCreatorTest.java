package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.support.RuleDataMother;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;

import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class PageRuleCreatorTest {
  @InjectMocks
  private PageRuleCreator pageRuleCreator;

  @Mock
  private WaRuleMetaDataRepository waRuleMetaDataRepository;

  @Mock
  private PageRuleHelper pageRuleJSHelper;

  @Mock
  RuleResponseMapper ruleResponseMapper;

  @BeforeEach
  void setup() {
    Mockito.reset(waRuleMetaDataRepository);
  }

  @Test
  void should_save_ruleRequest_details_to_DB() throws RuleException {

    CreateRuleRequest ruleRequest = RuleRequestMother.ruleRequest();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);
    when(ruleResponseMapper.mapRuleResponse(any(WaRuleMetadata.class))).thenReturn(getViewRuleResponse());
    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    // fix getting id, create query for max +1 for the id and then use that
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
    assertEquals(ruleRequest.ruleDescription(), ruleResponse.ruleDescription());
  }


  @Test
  void shouldGiveRuleExpressionInACorrectFormatForDateCompare() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.dateCompareRuleRequest();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);
    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);

    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForDisable() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.DisableRuleRequest();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);
    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForDisableIfAnySourceIsTruw() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.DisableRuleTestDataAnySourceIsTrue();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);

    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());

  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForEnableIfAnySourceIsTrue() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleTestDataAnySourceIsTrue();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());

  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForEnable() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleRequest();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());

  }

  //
  @Test
  void shouldGiveRuleExpressionInACorrectFormatForHide() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleRequest();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());

  }

  @Test()
  void shouldGiveRuleExpressionInACorrectFormatForHideIfAnySourceIsTrue() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleTestDataAnySourceIsTrue();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());

  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForRequireIfAnySourceIsTrue() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestDataAnySourceIsTrue();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());


  }


  @Test
  void shouldGiveRuleExpressionInACorrectFormatForRequireIfElsePart() throws RuleException {


    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData_othercomparator();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());

  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForUnhide() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequest();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());

  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForRequireIf() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleRequest();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void shouldGiveRuleExpressionInACorrectFormatForUnhideIfAnySourceIsTrue() throws RuleException {
    CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequestIfAnySource();
    RuleData ruleData = RuleDataMother.ruleData();
    Long userId = 99L;
    Long availableId = 1L;

    when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

    when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);
    ViewRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);

    Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());

  }

  private ViewRuleResponse getViewRuleResponse() {
    return new ViewRuleResponse(
        99l,
        123l,
        "Enable",
        "TestDescription",
        "sourceIdentifier",
        new ArrayList<>(),
        "comparator",
        "Question",
        "errorMsgText",
        Arrays.asList(new QuestionInfo("label1", "test456"), new QuestionInfo("label2", "test789"))
    );
  }

}
