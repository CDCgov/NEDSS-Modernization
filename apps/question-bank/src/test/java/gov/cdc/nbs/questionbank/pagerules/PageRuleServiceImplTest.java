package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.RuleDetails;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PageRuleServiceImplTest {
    @InjectMocks
    private PageRuleServiceImpl pageRuleServiceImpl;

    @Mock
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Mock
    private RuleCreatedEventProducer.EnabledProducer ruleCreatedEventProducer;

    @Before()
    public void setup(){
        Mockito.reset(waRuleMetaDataRepository);
        Mockito.reset(ruleCreatedEventProducer);
    }

    @Test
    public void should_save_ruleRequest_details_to_DB(){

        RuleDetails ruleDetails = new RuleDetails();
        ruleDetails.setId(999L);

        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.ruleRequest();

        Mockito.when(waRuleMetaDataRepository.save(Mockito.any())).thenReturn(ruleDetails);

        Long id = pageRuleServiceImpl.createPageRule(ruleRequest);

        Mockito.verify(waRuleMetaDataRepository,Mockito.times(1));

    }

    @Test
    public void should_send_ruleRequest_Event(){
        Instant now = Instant.now();
        RuleDetails ruleDetails = new RuleDetails();
        ruleDetails.setId(999L);
        ruleDetails.setAddTime(now);
        when(waRuleMetaDataRepository.save(Mockito.any())).thenReturn(ruleDetails);

        CreateRuleRequest.ruleRequest ruleRequest= RuleRequestMother.ruleRequest();

        Long id = pageRuleServiceImpl.createPageRule(ruleRequest);

        ArgumentCaptor<RuleCreatedEvent> eventCaptor = ArgumentCaptor.forClass(RuleCreatedEvent.class);
        Mockito.verify(ruleCreatedEventProducer, times(1)).send(eventCaptor.capture());

    }
}
