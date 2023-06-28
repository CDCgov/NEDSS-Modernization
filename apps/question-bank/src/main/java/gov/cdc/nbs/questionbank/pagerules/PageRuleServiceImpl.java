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
    private static final String UNHIDE= "Unhide";
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
        RuleDataHelper ruleDataHelper = checkRuleCD(request);
        WaRuleMetadata ruleMetadata =new WaRuleMetadata();
        ruleMetadata.setRuleCd(request.ruleFunction());
        ruleMetadata.setRuleDescText(request.ruleDescription());
        ruleMetadata.setSourceValues(ruleDataHelper.sourceValues());
        ruleMetadata.setLogic(request.comparator());
        ruleMetadata.setSourceQuestionIdentifier(ruleDataHelper.sourceIdentifier());
        ruleMetadata.setTargetQuestionIdentifier(ruleDataHelper.targetIdentifiers());
        ruleMetadata.setTargetType(request.targetType());
        ruleMetadata.setAddTime(Instant.now());
        ruleMetadata.setAddUserId(userId);
        ruleMetadata.setLastChgTime(Instant.now());
        ruleMetadata.setRecordStatusCd("ACTIVE");
        ruleMetadata.setLastChgUserId(userId);
        ruleMetadata.setRecordStatusTime(Instant.now());
        ruleMetadata.setErrormsgText(ruleDataHelper.errorMsgText());
        ruleMetadata.setJsFunction("Test JS Name");
        ruleMetadata.setJsFunctionName("Test");
        ruleMetadata.setWaTemplateUid(request.templateUid());
        ruleMetadata.setRuleExpression(ruleDataHelper.ruleExpression());

        return ruleMetadata;
    }


    private RuleDataHelper checkRuleCD(CreateRuleRequest.ruleRequest request){
        SourceValuesHelper sourceValuesHelper = sourceValuesHelper(request);
        String sourceText= sourceValuesHelper.sourceText();
        String sourceValueText= sourceValuesHelper.sourceValueText();
        String sourceIdentifier= sourceValuesHelper.sourceIdentifiers();
        RuleExpressionHelper expressionValues = null;
        TargetValuesHelper targetValuesHelper= targetValuesHelper(request);
        String targetIdentifier= targetValuesHelper.targetIdentifier();

        //Date Compare
        if(Objects.equals(request.ruleFunction(), DATE_COMPARE)){
          expressionValues = DataCompareFunction(request,sourceValuesHelper,targetValuesHelper);
        }
        //Disable
        if(Objects.equals(request.ruleFunction(),DISABLE)){
            expressionValues = disableFunction(request,sourceValuesHelper,targetValuesHelper);
        }
        if(Objects.equals(request.ruleFunction(),ENABLE)){
            expressionValues = EnableFunction(request,sourceValuesHelper,targetValuesHelper);
        }
        if(Objects.equals(request.ruleFunction(),HIDE)){
            expressionValues = hideFunction(request,sourceValuesHelper,targetValuesHelper);
        }
        if(Objects.equals(request.ruleFunction(),REQUIRE_IF)){
            expressionValues= requireIfFunction(request,sourceValuesHelper,targetValuesHelper);
        }
        if(Objects.equals(request.ruleFunction(),UNHIDE)){
            expressionValues= unHideFunction(request,sourceValuesHelper,targetValuesHelper);
        }
        assert expressionValues != null;
        return new RuleDataHelper(targetIdentifier,expressionValues.ruleExpression(),expressionValues.errorMessage(),sourceIdentifier,sourceText,sourceValueText);
    }
    private SourceValuesHelper sourceValuesHelper(CreateRuleRequest.ruleRequest request){
        String sourceText= request.sourceText();
        String sourceIdentifier= request.sourceIdentifier();
        List<CreateRuleRequest.sourceValues> sourceValueList= request.sourceValue();
        String sourceIds= null;
        String sourceValueText= null;
        if(request.sourceValue()!= null){
            for(CreateRuleRequest.sourceValues sourceValues: sourceValueList){
                sourceIds= String.join(",",sourceValues.sourceValueId());
               sourceValueText= String.join(",",sourceValues.sourceValueText());
            }
        }
        return new SourceValuesHelper(sourceIds, sourceValueText,sourceText,sourceIdentifier);
    }

    private TargetValuesHelper targetValuesHelper(CreateRuleRequest.ruleRequest request){
        List<String> targetTextList = request.targetValueText();
        List<String> targetValueIdentifierList= request.targetValueIdentifier();
        String targetIdentifier= String.join(",",targetValueIdentifierList);

        return new TargetValuesHelper(targetIdentifier,targetTextList);
    }

    private RuleExpressionHelper DataCompareFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper){
        List<String> errorMessageList= new ArrayList<>();
        String sourceIdentifier= sourceValuesHelper.sourceIdentifiers();
        String sourceText= sourceValuesHelper.sourceText();
        String targetIdentifier= targetValuesHelper.targetIdentifier();
        List<String> targetTextList= targetValuesHelper.targetTextList();
        String ruleExpression = sourceIdentifier.concat(" ")
                .concat(request.comparator()).concat(" ").concat("^ DT")
                .concat(" ").concat("( "+ targetIdentifier +" )");
        for(String targetText: targetTextList){
            String errMsg= sourceText.concat(" ").concat(MUST_BE).concat(" ").concat(request.comparator()).concat(" ").concat(targetText);
            errorMessageList.add(errMsg);
        }
        String errorMessageText= String.join(",",errorMessageList);

        return new RuleExpressionHelper(errorMessageText,ruleExpression);
    }

    private RuleExpressionHelper EnableFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper){
        String ruleExpression;
        List<String> errorMessageList= new ArrayList<>();
        String sourceIdentifier= sourceValuesHelper.sourceIdentifiers();
        String sourceText= sourceValuesHelper.sourceText();
        String targetIdentifier= targetValuesHelper.targetIdentifier();
        List<String> targetTextList= targetValuesHelper.targetTextList();
        String sourceIds= sourceValuesHelper.sourceValueIds();
        String sourceValueText= sourceValuesHelper.sourceValueText();
        String commonErrMsgForAnySource= sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat(ANY_SOURCE_VALUE).concat(" ");
        String commonRuleExpressionForAnySource= sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue=sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");

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
        String errorMessageText= String.join(",",errorMessageList);
        return new RuleExpressionHelper(errorMessageText,ruleExpression);
    }
    private RuleExpressionHelper disableFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper){
        String ruleExpression;
        List<String> errorMessageList= new ArrayList<>();
        String sourceIdentifier= sourceValuesHelper.sourceIdentifiers();
        String sourceText= sourceValuesHelper.sourceText();
        String targetIdentifier= targetValuesHelper.targetIdentifier();
        List<String> targetTextList= targetValuesHelper.targetTextList();
        String sourceIds= sourceValuesHelper.sourceValueIds();
        String sourceValueText= sourceValuesHelper.sourceValueText();
        String commonErrMsgForAnySource= sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat(ANY_SOURCE_VALUE).concat(" ");
        String commonRuleExpressionForAnySource= sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue=sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");

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
        String errorMessageText= String.join(",",errorMessageList);
        return new RuleExpressionHelper(errorMessageText,ruleExpression);
    }

    private RuleExpressionHelper hideFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper){
        String ruleExpression;
        List<String> errorMessageList= new ArrayList<>();
        String sourceIdentifier= sourceValuesHelper.sourceIdentifiers();
        String sourceText= sourceValuesHelper.sourceText();
        String targetIdentifier= targetValuesHelper.targetIdentifier();
        List<String> targetTextList= targetValuesHelper.targetTextList();
        String sourceIds= sourceValuesHelper.sourceValueIds();
        String sourceValueText= sourceValuesHelper.sourceValueText();
        String commonErrMsgForAnySource= sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat(ANY_SOURCE_VALUE).concat(" ");
        String commonRuleExpressionForAnySource= sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue=sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");

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
        String errorMessageText= String.join(",",errorMessageList);
        return new RuleExpressionHelper(errorMessageText,ruleExpression);
    }

    private RuleExpressionHelper requireIfFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper){
        String ruleExpression;
        List<String> errorMessageList= new ArrayList<>();
        String sourceIdentifier= sourceValuesHelper.sourceIdentifiers();
        String sourceText= sourceValuesHelper.sourceText();
        String targetIdentifier= targetValuesHelper.targetIdentifier();
        List<String> targetTextList= targetValuesHelper.targetTextList();
        String sourceIds= sourceValuesHelper.sourceValueIds();
        String sourceValueText= sourceValuesHelper.sourceValueText();
        String commonRuleExpressionForAnySource= sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue=sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");

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
        String errorMessageText= String.join(",",errorMessageList);
        return new RuleExpressionHelper(errorMessageText,ruleExpression);
    }

    private RuleExpressionHelper unHideFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper){
        String ruleExpression;
        List<String> errorMessageList= new ArrayList<>();
        String sourceIdentifier= sourceValuesHelper.sourceIdentifiers();
        String sourceText= sourceValuesHelper.sourceText();
        String targetIdentifier= targetValuesHelper.targetIdentifier();
        List<String> targetTextList= targetValuesHelper.targetTextList();
        String sourceIds= sourceValuesHelper.sourceValueIds();
        String sourceValueText= sourceValuesHelper.sourceValueText();
        String commonRuleExpressionForAnySource= sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue=sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");
        String  targetText= String.join(",",targetTextList);
        if(request.anySourceValue() && Objects.equals(request.comparator(), "=")){
            ruleExpression= commonRuleExpressionForAnySource.concat("^ S").concat(" ").concat("( "+ targetIdentifier +" )");
            String errMsg= sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat("(" +ANY_SOURCE_VALUE+ ")").concat(" ").concat(targetText);
            errorMessageList.add(errMsg);

        }else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ S").concat(" ").concat("(" + targetIdentifier + ")");
            String errMsg= sourceText.concat(" ").concat(request.comparator()).concat(MUST_BE).concat("(" +sourceValueText+ ")").concat(" ").concat(targetText);
            errorMessageList.add(errMsg);

        }
        String errorMessageText= String.join(",",errorMessageList);
        return new RuleExpressionHelper(errorMessageText,ruleExpression);
    }
}
