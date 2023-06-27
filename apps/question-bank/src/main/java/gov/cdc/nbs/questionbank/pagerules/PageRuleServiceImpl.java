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

    private static final String DISABLE= "Disable";
    private static final String ENABLE= "Enable";
    private static final String REQUIRE_IF= "Require If";
    private static final String HIDE= "Hide";
    private static final String MUST_BE= "must be";

    private static final String ANY_SOURCE_VALUE= "( Any Source Value )";

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
        ruleMetadata.setSourceValues(targetValueHelper.sourceValues());
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
        String sourceText= request.sourceText();
        String sourceIdentifier= request.sourceIdentifier();
        List<String> targetTextList = request.targetValueText();
        List<String> targetValueIdentifierList= request.targetValueIdentifier();
        String errorMessageText= null;
        List<String> errorMessageList= new ArrayList<>();
        List<CreateRuleRequest.sourceValues> sourceValueList= request.sourceValue();
        String sourceIds = null;
        String sourceValueText= null;
        String commonErrMsgForAnySource= sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat(ANY_SOURCE_VALUE).concat(" ");
        if(request.sourceValue()!= null){
            for(CreateRuleRequest.sourceValues sourceValues: sourceValueList){
                sourceIds= String.join(",",sourceValues.sourceValueId());
                sourceValueText= String.join(",",sourceValues.sourceValueText());
            }
        }
        //target values and Error Message text
        String targetIdentifier= String.join(",",targetValueIdentifierList);
        String commonRuleExpressionForAnySource= sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue=sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");
        //Date Compare
        if(Objects.equals(request.ruleFunction(), DATE_COMPARE)){
          ruleExpression = sourceIdentifier.concat(" ")
                    .concat(request.comparator()).concat(" ").concat("^ DT")
                    .concat(" ").concat("( "+ targetIdentifier +" )");
          for(String targetText: targetTextList){
              String errMsg= sourceText.concat(" ").concat(MUST_BE).concat(" ").concat(request.comparator()).concat(" ").concat(targetText);
              errorMessageList.add(errMsg);
          }
          errorMessageText= String.join(",",errorMessageList);
        }
        //Disable
        if(Objects.equals(request.ruleFunction(),DISABLE)){
            if(request.anySourceValue() && Objects.equals(request.comparator(), "=")){
                ruleExpression= commonRuleExpressionForAnySource.concat("^ D").concat(" ").concat("( "+ targetIdentifier +" )");
                for(String targetText: targetTextList){
                    String errMsg= commonErrMsgForAnySource.concat(targetText);
                    errorMessageList.add(errMsg);
                }
            }else {
                ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ D").concat(" ").concat("(" + targetIdentifier + ")");
                for(String targetText: targetTextList){
                    String errMsg= sourceText.concat(" ").concat(request.comparator()).concat(" ").concat(MUST_BE).concat(" ").concat("(" +sourceValueText+ ")").concat(" ").concat(targetText);
                    errorMessageList.add(errMsg);
                }
            }
            errorMessageText= String.join(",",errorMessageList);
        }
        if(Objects.equals(request.ruleFunction(),ENABLE)){
            if(request.anySourceValue() && Objects.equals(request.comparator(), "=")){
                ruleExpression= commonRuleExpressionForAnySource.concat("^ E").concat(" ").concat("( "+ targetIdentifier +" )");
                for(String targetText: targetTextList){
                    String errMsg= commonErrMsgForAnySource.concat(targetText);
                    errorMessageList.add(errMsg);
                }
            }else {
                ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ E").concat(" ").concat("(" + targetIdentifier + ")");
                for(String targetText: targetTextList){
                    String errMsg= sourceText.concat(" ").concat(request.comparator()).concat(" ").concat(MUST_BE).concat(" ").concat("(" +sourceValueText+ ")").concat(" ").concat(targetText);
                    errorMessageList.add(errMsg);
                }
            }
            errorMessageText= String.join(",",errorMessageList);
        }
        if(Objects.equals(request.ruleFunction(),HIDE)){
            if(request.anySourceValue() && Objects.equals(request.comparator(), "=")){
                ruleExpression= commonRuleExpressionForAnySource.concat("^ H").concat(" ").concat("( "+ targetIdentifier +" )");
                for(String targetText: targetTextList){
                    String errMsg= commonErrMsgForAnySource.concat(targetText);
                    errorMessageList.add(errMsg);
                }
            }else {
                ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ H").concat(" ").concat("(" + targetIdentifier + ")");
                for(String targetText: targetTextList){
                    String errMsg= sourceText.concat(" ").concat(request.comparator()).concat(" ").concat(MUST_BE).concat(" ").concat("(" +sourceValueText+ ")").concat(" ").concat(targetText);
                    errorMessageList.add(errMsg);
                }
            }
            errorMessageText= String.join(",",errorMessageList);
        }
        if(Objects.equals(request.ruleFunction(),REQUIRE_IF)){
            if(request.anySourceValue() && Objects.equals(request.comparator(), "=")){
                ruleExpression= commonRuleExpressionForAnySource.concat("^ R").concat(" ").concat("( "+ targetIdentifier +" )");
                for(String targetText: targetTextList){
                    String errMsg= sourceText.concat(" ").concat(" ").concat(" ").concat(ANY_SOURCE_VALUE).concat(" ").concat(targetText).concat(" ").concat("is required");
                    errorMessageList.add(errMsg);
                }
            }else {
                ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ R").concat(" ").concat("(" + targetIdentifier + ")");
                for(String targetText: targetTextList){
                    String errMsg= sourceText.concat(" ").concat(request.comparator()).concat(" ").concat("(" +sourceValueText+ ")").concat(" ").concat(targetText).concat(" ").concat("is required");
                    errorMessageList.add(errMsg);
                }
            }
            errorMessageText= String.join(",",errorMessageList);
        }
       return new TargetValueHelper(targetIdentifier,ruleExpression,errorMessageText,sourceIdentifier,sourceText,sourceValueText);
    }
}
