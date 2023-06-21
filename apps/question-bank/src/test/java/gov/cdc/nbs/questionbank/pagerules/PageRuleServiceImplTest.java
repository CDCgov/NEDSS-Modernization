package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.RuleDetails;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



import java.math.BigInteger;
import java.time.Instant;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageRuleServiceImplTest {
    @InjectMocks
    private PageRuleServiceImpl pageRuleServiceImpl;

    @Mock
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Mock
    private RuleCreatedEventProducer.EnabledProducer ruleCreatedEventProducer;

    @org.junit.jupiter.api.BeforeEach()
    void setup(){
        Mockito.reset(waRuleMetaDataRepository);
        Mockito.reset(ruleCreatedEventProducer);
    }

    @Test
    void should_save_ruleRequest_details_to_DB(){

        RuleDetails ruleDetails = new RuleDetails();
        ruleDetails.setId(BigInteger.valueOf(12345678));

        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.ruleRequest();

        Mockito.when(waRuleMetaDataRepository.save(Mockito.any())).thenReturn(ruleDetails);


        BigInteger id = pageRuleServiceImpl.createPageRule(ruleRequest);

        Mockito.verify(waRuleMetaDataRepository, Mockito.times(1)).save(Mockito.any());
        Assertions.assertEquals(BigInteger.valueOf(ruleDetails.getWaTemplateUid()), new BigInteger(String.valueOf(id)));


    }

    @Test
    void should_send_ruleRequest_Event(){
        Instant now = Instant.now();
        RuleDetails ruleDetails = new RuleDetails();
        ruleDetails.setId(BigInteger.valueOf(999));

        when(waRuleMetaDataRepository.save(Mockito.any())).thenReturn(ruleDetails);

        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.ruleRequest();

        BigInteger id = pageRuleServiceImpl.createPageRule(ruleRequest);

        ArgumentCaptor<RuleCreatedEvent> eventCaptor = ArgumentCaptor.forClass(RuleCreatedEvent.class);
        Mockito.verify(ruleCreatedEventProducer, times(1)).send(eventCaptor.capture());
        Assertions.assertEquals(BigInteger.valueOf(ruleDetails.getWaTemplateUid()), new BigInteger(String.valueOf(id)));



    }
}
