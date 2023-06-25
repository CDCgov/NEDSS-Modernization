package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PageRuleServiceImpl implements PageRuleService {
    private final  WaRuleMetaDataRepository waRuleMetaDataRepository;

    private final RuleCreatedEventProducer ruleCreatedEventProducer;

    private static final String DATE_COMPARE= "Date Compare";

    public PageRuleServiceImpl(WaRuleMetaDataRepository waRuleMetaDataRepository, RuleCreatedEventProducer ruleCreatedEventProducer) {
        this.waRuleMetaDataRepository = waRuleMetaDataRepository;
        this.ruleCreatedEventProducer= ruleCreatedEventProducer;
    }

    public CreateRuleResponse createPageRule(Long userId, CreateRuleRequest.ruleRequest request){
        WaRuleMetadata waRuleMetadata= setRuleDataValues(userId,request);
       try{
           waRuleMetaDataRepository.save(waRuleMetadata);
       }catch (Exception exception ){
           return new CreateRuleResponse(null,exception.getMessage());
       }
       try {
           sendRuleEvent(request);
       }catch(Exception e){
           return new CreateRuleResponse(null,e.getMessage());
       }
       return  new CreateRuleResponse(waRuleMetadata.getId(),"Rule Created Successfully");

    }

    private void sendRuleEvent(CreateRuleRequest.ruleRequest ruleRequest) {
        ruleCreatedEventProducer.send(new RuleCreatedEvent(ruleRequest));
    }

    private WaRuleMetadata setRuleDataValues(Long userId, CreateRuleRequest.ruleRequest request){
        TargetValueHelper targetValueHelper= checkRuleCD(request);
        WaRuleMetadata ruleMetadata =new WaRuleMetadata();
        ruleMetadata.setRuleCd(request.ruleFunction());
        ruleMetadata.setRuleDescText(request.ruleDescription());
        ruleMetadata.setSourceValues(request.sourceValue());
        ruleMetadata.setLogic(request.comparator());
        ruleMetadata.setSourceQuestionIdentifier(targetValueHelper.sourceIdentifier());
        ruleMetadata.setTargetQuestionIdentifier(targetValueHelper.targetIdentifiers());
        ruleMetadata.setTargetType(request.targetType());
        ruleMetadata.setAddTime(Instant.now());
        ruleMetadata.setAddUserId(userId);
        ruleMetadata.setLastChgTime(Instant.now());
        ruleMetadata.setRecordStatusCd("ACTIVE");
        ruleMetadata.setLastChgUserId(userId);
        ruleMetadata.setRecordStatusTime(Instant.now());
        ruleMetadata.setErrormsgText(targetValueHelper.errorMsgText());
        ruleMetadata.setJsFunction("Test JS Name");
        ruleMetadata.setJsFunctionName("Test");
        ruleMetadata.setWaTemplateUid(request.templateUid());
        ruleMetadata.setRuleExpression(targetValueHelper.ruleExpression());

        return ruleMetadata;
    }

    private TargetValueHelper checkRuleCD(CreateRuleRequest.ruleRequest request){
        String ruleExpression= null;
        String source= request.source();
        List<String> targetList = request.targetValue();
        List<String> identifiers= new ArrayList<>();
        List<String> targetTextValues= new ArrayList<>();
        String errorMessageText= null;
        List<String> errorMessageList= new ArrayList<>();

        //sourceIdentifier
        String sourceIdentifier = source.substring(source.indexOf("(")+1, source.indexOf(")"));
        String sourceText= source.substring(0,source.indexOf("(")).trim();

        //target values and Error Message text
        for(String targetValue: targetList){
            String identifier= targetValue.substring(targetValue.indexOf("(")+1, targetValue.indexOf(")"));
            String targetText= targetValue.substring(0,targetValue.indexOf("(")).trim();
            targetTextValues.add(targetText);
            identifiers.add(identifier);
        }
        String targetIdentifierValues= String.join(",",identifiers);
        if(Objects.equals(request.ruleFunction(), DATE_COMPARE)){
          ruleExpression = sourceIdentifier.concat(" ")
                    .concat(request.comparator()).concat(" ").concat("^ DT")
                    .concat(" ").concat("( "+ targetIdentifierValues +" )");
          for(String targetText: targetTextValues){
              String errMsg= sourceText.concat(" ").concat("must be").concat(" ").concat(request.comparator()).concat(" ").concat(targetText);
              errorMessageList.add(errMsg);
          }
          errorMessageText= String.join(",",errorMessageList);
        }
       return new TargetValueHelper(targetIdentifierValues,targetTextValues,ruleExpression,errorMessageText,sourceIdentifier,sourceText);
    }
}
