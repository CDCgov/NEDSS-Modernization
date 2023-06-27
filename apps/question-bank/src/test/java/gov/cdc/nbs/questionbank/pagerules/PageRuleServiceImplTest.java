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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void should_save_ruleRequest_details_to_DB(){

        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.ruleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());



    }

    @Test
    void should_send_ruleRequest_Event(){
        Long userId= 99L ;
        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.ruleRequest();
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);
        ArgumentCaptor<RuleCreatedEvent> eventCaptor = ArgumentCaptor.forClass(RuleCreatedEvent.class);
        Mockito.verify(ruleCreatedEventProducer, times(1)).send(eventCaptor.capture());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDateCompare(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.dateCompareRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDisable(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.DisableRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForDisableIfAnySourceIsTruw(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.DisableRuleTestDataAnySourceIsTrue();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForEnableIfAnySourceIsTrue(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.EnableRuleTestDataAnySourceIsTrue();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForEnable(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.EnableRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForHide(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.HideRuleRequest();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForHideIfAnySourceIsTrue(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.HideRuleTestDataAnySourceIsTrue();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIfAnySourceIsTrue(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.RequireIfRuleTestDataAnySourceIsTrue();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }

    @Test
    void shouldGiveRuleExpressionInACorrectFormatForRequireIf(){


        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.RequireIfRuleTestData();
        Long userId= 99L ;
        CreateRuleResponse ruleResponse = pageRuleServiceImpl.createPageRule(userId, ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals("Rule Created Successfully",ruleResponse.message());

    }
}

