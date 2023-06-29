package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.BadAttributeValueExpException;

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
    @BeforeEach
    void setup(){
        Mockito.reset(waRuleMetaDataRepository);
        Mockito.reset(ruleCreatedEventProducer);
    }

    @Test
    void should_save_ruleRequest_details_to_DB() throws BadAttributeValueExpException{

        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.ruleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());



    }

    @Test
    void should_send_ruleRequest_Event() throws BadAttributeValueExpException{
        Long userId= 99L ;
        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.ruleRequest();
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);
        ArgumentCaptor<RuleCreatedEvent> eventCaptor = ArgumentCaptor.forClass(RuleCreatedEvent.class);
        Mockito.verify(ruleCreatedEventProducer, times(1)).send(eventCaptor.capture());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDateCompare() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.dateCompareRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDisable() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.DisableRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);
        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDisableIfAnySourceIsTruw() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.DisableRuleTestDataAnySourceIsTrue();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForEnableIfAnySourceIsTrue() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.EnableRuleTestDataAnySourceIsTrue();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForEnable() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.EnableRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForHide() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.HideRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForHideIfAnySourceIsTrue() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.HideRuleTestDataAnySourceIsTrue();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIfAnySourceIsTrue() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.RequireIfRuleTestDataAnySourceIsTrue();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForUnhide() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.UnhideRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIf() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.RequireIfRuleTestData();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldThrowAnExceptionIfThereIsSomethingWrongInFunctionName(){
        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.InvalidRuleRequest();
        Long userId= 99L ;
        BadAttributeValueExpException exception= assertThrows(BadAttributeValueExpException.class,()-> pageRuleServiceImpl.createPageRule(userId, ruleRequest));

        assertEquals("BadAttributeValueException: Error in Creating Rule Expression and Error Message Text",exception.toString());
    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForUnhideIfAnySourceIsTrue() throws BadAttributeValueExpException{


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.UnhideRuleRequestIfAnySource();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }
}

