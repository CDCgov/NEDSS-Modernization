package gov.cdc.nbs.questionbank.businessrules;

import gov.cdc.nbs.questionbank.businessrules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.businessRule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.RuleBasedCollator;

@Service
public class BusinessRulesServiceImpl implements BusinessRuleService{

    @Autowired
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Autowired
    private RuleCreatedEventProducer ruleCreatedEventProducer;


    public Long createBusinessRule(CreateRuleRequest createRuleRequest){

       WaRuleMetadata ruleMetadata = new WaRuleMetadata();
       String sourceValue= (createRuleRequest.getRuleDetails().getAnySourceValue()!=null) ? createRuleRequest.getRuleDetails().getAnySourceValue():createRuleRequest.getRuleDetails().getSourceValue();
       ruleMetadata.setRuleCd(createRuleRequest.getRuleDescription().getRuleFunction());
       ruleMetadata.setRuleDescText(createRuleRequest.getRuleDescription().getRuleDescription());
       ruleMetadata.setLogic(createRuleRequest.getRuleDetails().getComparator());
       ruleMetadata.setTargetType(createRuleRequest.getRuleDetails().getTargetType());
       ruleMetadata.setTargetQuestionIdentifier(createRuleRequest.getRuleDetails().getTargetValue());
       ruleMetadata.setSourceValues(sourceValue);
       ruleMetadata.setSourceQuestionIdentifier(createRuleRequest.getRuleDetails().getSource());
       waRuleMetaDataRepository.save(ruleMetadata);
       sendRuleEvent(createRuleRequest);
       return ruleMetadata.getWaTemplateUid();

    }

    private void sendRuleEvent(CreateRuleRequest createRuleRequest){
        ruleCreatedEventProducer.send(new RuleCreatedEvent(createRuleRequest));
    }
}
