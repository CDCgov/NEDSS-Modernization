package gov.cdc.nbs.questionbank.businessrules;

import gov.cdc.nbs.questionbank.businessrules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.businessRule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.RuleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageRulesServiceImpl implements PageRuleService {

    @Autowired
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Autowired
    private RuleCreatedEventProducer ruleCreatedEventProducer;


    public Long createPageRule(CreateRuleRequest.ruleRequest request){

       WaRuleMetadata ruleMetadata =new RuleDetails(asAdd(request));
       waRuleMetaDataRepository.save(ruleMetadata);
       sendRuleEvent(request);
       return ruleMetadata.getWaTemplateUid();

    }

    private void sendRuleEvent(CreateRuleRequest.ruleRequest ruleRequest){
        ruleCreatedEventProducer.send(new RuleCreatedEvent(ruleRequest));
    }

    CreateRuleRequest.ruleRequest asAdd(CreateRuleRequest.ruleRequest ruleRequest){

        return new CreateRuleRequest.ruleRequest(
                ruleRequest.ruleFunction(),
                ruleRequest.ruleDescription(),
                ruleRequest.source(),
                ruleRequest.anySourceValue(),
                ruleRequest.comparator(),
                ruleRequest.sourceValue(),
                ruleRequest.targetType(),
                ruleRequest.targetValue()
        );
    }
}
