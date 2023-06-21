package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.pagerules.command.RuleCommand;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.RuleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class PageRuleServiceImpl implements PageRuleService {

    @Autowired
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Autowired
    private RuleCreatedEventProducer ruleCreatedEventProducer;


    public BigInteger createPageRule(CreateRuleRequest.ruleRequest request){

       WaRuleMetadata ruleMetadata =new RuleDetails(asAdd(request));
       waRuleMetaDataRepository.save(ruleMetadata);
       sendRuleEvent(request);
       return ruleMetadata.getId();

    }

    private void sendRuleEvent(CreateRuleRequest.ruleRequest ruleRequest){
        ruleCreatedEventProducer.send(new RuleCreatedEvent(ruleRequest));
    }

    RuleCommand.AddTextRule asAdd(CreateRuleRequest.ruleRequest request){

        return new RuleCommand.AddTextRule(
                request.mask(),
                request.fieldLength(),
                request.defaultValue(),
                ruleData(request)
        );
    }

    RuleCommand.RuleData ruleData(CreateRuleRequest createRuleRequest){
        return new RuleCommand.RuleData(
                createRuleRequest.ruleFunction(),
                createRuleRequest.ruleDescription(),
                createRuleRequest.source(),
                createRuleRequest.anySourceValue(),
                createRuleRequest.comparator(),
                createRuleRequest.sourceValue(),
                createRuleRequest.targetType(),
                createRuleRequest.targetValue()
        );
    }
}
