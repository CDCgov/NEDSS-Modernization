package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
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
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleDataMother;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;

@ExtendWith(MockitoExtension.class)
class PageRuleCreatorTest {
    @InjectMocks
    private PageRuleCreator pageRuleCreator;

    @Mock
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Mock
    private PageRuleHelper pageRuleJSHelper;

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

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        // fix getting id, create query for max +1 for the id and then use that
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }


    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDateCompare() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.dateCompareRuleRequest();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDisable() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.DisableRuleRequest();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDisableIfAnySourceIsTruw() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.DisableRuleTestDataAnySourceIsTrue();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForEnableIfAnySourceIsTrue() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleTestDataAnySourceIsTrue();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForEnable() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.EnableRuleRequest();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForHide() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleRequest();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test()
    void shouldGiveRuleExpressionInACorrectFormatForHideIfAnySourceIsTrue() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.HideRuleTestDataAnySourceIsTrue();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIfAnySourceIsTrue() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestDataAnySourceIsTrue();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }


    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIfElsePart() throws RuleException {


        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleTestData_othercomparator();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }



    @Test
    void shouldGiveRuleExpressionInACorrectFormatForUnhide() throws RuleException {
        CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequest();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());
    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIf() throws RuleException {
        CreateRuleRequest ruleRequest = RuleRequestMother.RequireIfRuleRequest();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());
    }

    @Test
    void shouldThrowAnExceptionIfThereIsSomethingWrongInFunctionName() {
        CreateRuleRequest ruleRequest = RuleRequestMother.InvalidRuleRequest();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);

        RuleException exception =
                assertThrows(RuleException.class,
                        () -> pageRuleCreator.createPageRule(userId, ruleRequest, 123456L));

        assertEquals(
                "gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException: Error in Creating Rule Expression and Error Message Text",
                exception.toString());
    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForUnhideIfAnySourceIsTrue() throws RuleException {
        CreateRuleRequest ruleRequest = RuleRequestMother.UnhideRuleRequestIfAnySource();
        RuleData ruleData = RuleDataMother.ruleData();
        Long userId = 99L;
        Long availableId = 1L;

        when(waRuleMetaDataRepository.findNextAvailableID()).thenReturn(availableId);

        when(pageRuleJSHelper.createRuleData(ruleRequest, availableId)).thenReturn(ruleData);
        CreateRuleResponse ruleResponse = pageRuleCreator.createPageRule(userId, ruleRequest, 123456L);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully", ruleResponse.message());

    }
}
