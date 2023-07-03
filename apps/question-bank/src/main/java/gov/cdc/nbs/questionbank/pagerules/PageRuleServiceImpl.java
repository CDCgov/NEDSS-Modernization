package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;

import org.springframework.stereotype.Service;

import javax.management.BadAttributeValueExpException;
import java.time.Instant;
import java.util.*;

@Service
public class PageRuleServiceImpl implements PageRuleService {
    private final WaRuleMetaDataRepository waRuleMetaDataRepository;

    private final RuleCreatedEventProducer ruleCreatedEventProducer;

    private static final String DATE_COMPARE = "Date Compare";

    private static final String DISABLE = "Disable";
    private static final String ENABLE = "Enable";
    private static final String REQUIRE_IF = "Require If";
    private static final String HIDE = "Hide";
    private static final String UNHIDE = "Unhide";
    private static final String MUST_BE = "must be";

    private static final String PARANTHESIS = "');\n";

    private static final String ANY_SOURCE_VALUE = "( Any Source Value )";

    private static final String PG_SUBSECTIONDISABLED = "pgSubSectionDisabled('";

    private static final String PG_SUBSECTIONENABLED = "pgSubSectionEnabled('";

    private static final String PG_ENABLE_ELEMENT = "pgEnableElement('";

    private static final String PG_DISABLE_ELEMENT = "pgDisableElement('";

    public PageRuleServiceImpl(WaRuleMetaDataRepository waRuleMetaDataRepository, RuleCreatedEventProducer ruleCreatedEventProducer) {
        this.waRuleMetaDataRepository = waRuleMetaDataRepository;
        this.ruleCreatedEventProducer = ruleCreatedEventProducer;
    }

    public CreateRuleResponse createPageRule(Long userId, CreateRuleRequest.ruleRequest request) throws BadAttributeValueExpException {
        WaRuleMetadata waRuleMetadata = setRuleDataValues(userId, request);
        waRuleMetaDataRepository.save(waRuleMetadata);
        sendRuleEvent(request);
        return new CreateRuleResponse(waRuleMetadata.getId(), "Rule Created Successfully");

    }

    private void sendRuleEvent(CreateRuleRequest.ruleRequest ruleRequest) {
        ruleCreatedEventProducer.send(new RuleCreatedEvent(ruleRequest));
    }

    private WaRuleMetadata setRuleDataValues(Long userId, CreateRuleRequest.ruleRequest request) throws BadAttributeValueExpException {

        RuleDataHelper ruleDataHelper;
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        ruleDataHelper = checkRuleCD(request,ruleMetadata);
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
        ruleMetadata.setJsFunction(ruleDataHelper.jsFunctionNameHelper().JsFunction());
        ruleMetadata.setJsFunctionName(ruleDataHelper.jsFunctionNameHelper().JsFunctionName());
        ruleMetadata.setWaTemplateUid(request.templateUid());
        ruleMetadata.setRuleExpression(ruleDataHelper.ruleExpression());

        return ruleMetadata;
    }


    private RuleDataHelper checkRuleCD(CreateRuleRequest.ruleRequest request, WaRuleMetadata ruleMetadata) throws BadAttributeValueExpException {
        SourceValuesHelper sourceValuesHelper = sourceValuesHelper(request);
        String sourceText = sourceValuesHelper.sourceText();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        RuleExpressionHelper expressionValues = null;
        TargetValuesHelper targetValuesHelper = targetValuesHelper(request);
        String targetIdentifier = targetValuesHelper.targetIdentifier();

        //Date Compare
        if (Objects.equals(request.ruleFunction(), DATE_COMPARE)) {
            expressionValues = DataCompareFunction(request, sourceValuesHelper, targetValuesHelper,ruleMetadata);
        }
        //Disable
        if (Objects.equals(request.ruleFunction(), DISABLE)) {
            expressionValues = disableFunction(request, sourceValuesHelper, targetValuesHelper,ruleMetadata);
        }
        if (Objects.equals(request.ruleFunction(), ENABLE)) {
            expressionValues = EnableFunction(request, sourceValuesHelper, targetValuesHelper,ruleMetadata);
        }
        if (Objects.equals(request.ruleFunction(), HIDE)) {
            expressionValues = hideFunction(request, sourceValuesHelper, targetValuesHelper,ruleMetadata);
        }
        if (Objects.equals(request.ruleFunction(), REQUIRE_IF)) {
            expressionValues = requireIfFunction(request, sourceValuesHelper, targetValuesHelper,ruleMetadata);
        }
        if (Objects.equals(request.ruleFunction(), UNHIDE)) {
            expressionValues = unHideFunction(request, sourceValuesHelper, targetValuesHelper,ruleMetadata);
        }
        if (expressionValues != null) {
            return new RuleDataHelper(targetIdentifier, expressionValues.ruleExpression(), expressionValues.errorMessage(), sourceIdentifier, sourceText, sourceValueText, expressionValues.jsFunctionNameHelper());
        } else {
            throw new BadAttributeValueExpException("Error in Creating Rule Expression and Error Message Text");
        }
    }

    private SourceValuesHelper sourceValuesHelper(CreateRuleRequest.ruleRequest request) {
        String sourceText = request.sourceText();
        String sourceIdentifier = request.sourceIdentifier();
        List<CreateRuleRequest.sourceValues> sourceValueList = request.sourceValue();
        String sourceIds = null;
        String sourceValueText = null;
        if (request.sourceValue() != null) {
            for (CreateRuleRequest.sourceValues sourceValues : sourceValueList) {
                sourceIds = String.join(",", sourceValues.sourceValueId());
                sourceValueText = String.join(",", sourceValues.sourceValueText());
            }
        }
        return new SourceValuesHelper(sourceIds, sourceValueText, sourceText, sourceIdentifier);
    }

    private TargetValuesHelper targetValuesHelper(CreateRuleRequest.ruleRequest request) {
        List<String> targetTextList = request.targetValueText();
        List<String> targetValueIdentifierList = request.targetValueIdentifier();
        String targetIdentifier = String.join(",", targetValueIdentifierList);

        return new TargetValuesHelper(targetIdentifier, targetTextList);
    }

    private RuleExpressionHelper DataCompareFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper,WaRuleMetadata ruleMetadata) {
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String ruleExpression = sourceIdentifier.concat(" ")
                .concat(request.comparator()).concat(" ").concat("^ DT")
                .concat(" ").concat("( " + targetIdentifier + " )");
        for (String targetText : targetTextList) {
            String errMsg = sourceText.concat(" ").concat(MUST_BE).concat(" ").concat(request.comparator()).concat(" ").concat(targetText);
            errorMessageList.add(errMsg);
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper = jsForDateCompare(request, sourceValuesHelper, targetValuesHelper,ruleMetadata);

        return new RuleExpressionHelper(errorMessageText, ruleExpression,new JSFunctionNameHelper(jsFunctionNameHelper.JsFunction(), jsFunctionNameHelper.JsFunctionName()));
    }

    private RuleExpressionHelper EnableFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper,WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String commonErrMsgForAnySource = sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat(ANY_SOURCE_VALUE).concat(" ");
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");

        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression = commonRuleExpressionForAnySource.concat("^ E").concat(" ").concat("( " + targetIdentifier + " )");
            for (String targetText : targetTextList) {
                String errMsg = commonErrMsgForAnySource.concat(targetText);
                errorMessageList.add(errMsg);
            }
        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ E").concat(" ").concat("(" + targetIdentifier + ")");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ").concat(request.comparator()).concat(" ").concat(MUST_BE).concat(" ").concat("(" + sourceValueText + ")").concat(" ").concat(targetText);
                errorMessageList.add(errMsg);
            }
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper = jsForEnableDisable(request, sourceValuesHelper, ruleMetadata);
        return new RuleExpressionHelper(errorMessageText, ruleExpression, new JSFunctionNameHelper(jsFunctionNameHelper.JsFunction(), jsFunctionNameHelper.JsFunctionName()));
    }

    private RuleExpressionHelper disableFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper,WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String commonErrMsgForAnySource = sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat(ANY_SOURCE_VALUE).concat(" ");
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");

        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression = commonRuleExpressionForAnySource.concat("^ D").concat(" ").concat("( " + targetIdentifier + " )");
            for (String targetText : targetTextList) {
                String errMsg = commonErrMsgForAnySource.concat(targetText);
                errorMessageList.add(errMsg);
            }
        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ D").concat(" ").concat("(" + targetIdentifier + ")");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ").concat(request.comparator()).concat(" ").concat(MUST_BE).concat(" ").concat("(" + sourceValueText + ")").concat(" ").concat(targetText);
                errorMessageList.add(errMsg);
            }
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper = jsForEnableDisable(request, sourceValuesHelper, ruleMetadata);
        return new RuleExpressionHelper(errorMessageText, ruleExpression, new JSFunctionNameHelper(jsFunctionNameHelper.JsFunction(), jsFunctionNameHelper.JsFunctionName()));
    }

    private RuleExpressionHelper hideFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper,WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String commonErrMsgForAnySource = sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat(ANY_SOURCE_VALUE).concat(" ");
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");

        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression = commonRuleExpressionForAnySource.concat("^ H").concat(" ").concat("( " + targetIdentifier + " )");
            for (String targetText : targetTextList) {
                String errMsg = commonErrMsgForAnySource.concat(targetText);
                errorMessageList.add(errMsg);
            }
        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ H").concat(" ").concat("(" + targetIdentifier + ")");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ").concat(request.comparator()).concat(" ").concat(MUST_BE).concat(" ").concat("(" + sourceValueText + ")").concat(" ").concat(targetText);
                errorMessageList.add(errMsg);
            }
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper= jsForHideAndUnhide(request,sourceValuesHelper,ruleMetadata);
        return new RuleExpressionHelper(errorMessageText, ruleExpression, new JSFunctionNameHelper(jsFunctionNameHelper.JsFunction(), jsFunctionNameHelper.JsFunctionName()));
    }

    private RuleExpressionHelper requireIfFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper,WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");

        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression = commonRuleExpressionForAnySource.concat("^ R").concat(" ").concat("( " + targetIdentifier + " )");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ").concat(" ").concat(" ").concat(ANY_SOURCE_VALUE).concat(" ").concat(targetText).concat(" ").concat("is required");
                errorMessageList.add(errMsg);
            }
        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ R").concat(" ").concat("(" + targetIdentifier + ")");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ").concat(request.comparator()).concat(" ").concat("(" + sourceValueText + ")").concat(" ").concat(targetText).concat(" ").concat("is required");
                errorMessageList.add(errMsg);
            }
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper= requireIfJsFunction(request,sourceValuesHelper,ruleMetadata,targetValuesHelper);
        return new RuleExpressionHelper(errorMessageText, ruleExpression, new JSFunctionNameHelper(jsFunctionNameHelper.JsFunction(),jsFunctionNameHelper.JsFunctionName()));
    }

    private RuleExpressionHelper unHideFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper,WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ").concat("( )").concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ").concat("(" + sourceIds + ")").concat(" ");
        String targetText = String.join(",", targetTextList);
        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression = commonRuleExpressionForAnySource.concat("^ S").concat(" ").concat("( " + targetIdentifier + " )");
            String errMsg = sourceText.concat(" ").concat(" ").concat(MUST_BE).concat(" ").concat("(" + ANY_SOURCE_VALUE + ")").concat(" ").concat(targetText);
            errorMessageList.add(errMsg);

        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator()).concat(" ").concat("^ S").concat(" ").concat("(" + targetIdentifier + ")");
            String errMsg = sourceText.concat(" ").concat(request.comparator()).concat(MUST_BE).concat("(" + sourceValueText + ")").concat(" ").concat(targetText);
            errorMessageList.add(errMsg);

        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper= jsForHideAndUnhide(request,sourceValuesHelper,ruleMetadata);
        return new RuleExpressionHelper(errorMessageText, ruleExpression, new JSFunctionNameHelper(jsFunctionNameHelper.JsFunction(), jsFunctionNameHelper.JsFunctionName()));
    }

    private JSFunctionNameHelper jsForDateCompare(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, TargetValuesHelper targetValuesHelper,WaRuleMetadata ruleMetadata) {
        StringBuilder stringBuffer = new StringBuilder();
        StringBuilder firstSB = new StringBuilder();
        StringBuilder secondSB = new StringBuilder();
        String sourceQuestionIdentifier = request.sourceIdentifier();
        String jsFunctionName = "ruleDComp" + sourceQuestionIdentifier +ruleMetadata.getId() ;
        stringBuffer.append(jsFunctionName);
        stringBuffer.append("    var i = 0;\n    var errorElts = new Array(); \n    var errorMsgs = new Array(); \n");
        firstSB.append("\n if ((getElementByIdOrByName(\"").append(sourceValuesHelper.sourceIdentifiers()).append("\").value)==''){ \n return {elements : errorElts, labels : errorMsgs}; }");
        secondSB.append("\n var sourceStr =getElementByIdOrByName(\"").append(sourceValuesHelper.sourceIdentifiers()).append("\").value;");
        secondSB.append("\n var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);");
        secondSB.append("\n var targetElt;\n var targetStr = ''; \n var targetDate = '';");
        Collection<String> coll = request.targetValueIdentifier();
        for (String targetQuestionIdentifier : coll) {
            //check for null just in case the target got deleted or is not visible except for edit
            secondSB.append("\n targetStr =getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim()).append("\") == null ? \"\" :getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim()).append("\").value;");
            secondSB.append("\n if (targetStr!=\"\") {");
            secondSB.append("\n    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);");
            secondSB.append("\n if (!(srcDate ");
            secondSB.append(request.comparator());
            secondSB.append(" targetDate)) {");
            secondSB.append("\n var srcDateEle=getElementByIdOrByName(\"").append(sourceValuesHelper.sourceIdentifiers()).append("\");");
            secondSB.append("\n var targetDateEle=getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim()).append("\");");
            secondSB.append("\n var srca2str=buildErrorAnchorLink(srcDateEle," + "\"").append(sourceValuesHelper.sourceIdentifiers()).append("\");");
            secondSB.append("\n var targeta2str=buildErrorAnchorLink(targetDateEle,\"").append(targetValuesHelper.targetIdentifier().trim()).append("\");");
            secondSB.append("\n    errorMsgs[i]=srca2str + \" must be ").append(request.comparator()).append(" \" + targeta2str; ");
            secondSB.append("\n    colorElementLabelRed(srcDateEle); ");
            secondSB.append("\n    colorElementLabelRed(targetDateEle); \n");

            secondSB.append("errorElts[i++]=getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim()).append("\"); \n");
            secondSB.append("}\n  }");
        }
        stringBuffer.append(firstSB);
        stringBuffer.append(secondSB);
        stringBuffer.append("\n return {elements : errorElts, labels : errorMsgs}\n}");
        return new JSFunctionNameHelper(stringBuffer.toString(), jsFunctionName + "()");
    }

    private JSFunctionNameHelper jsForEnableDisable(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, WaRuleMetadata ruleMetadata) {
        StringBuilder buffer = new StringBuilder();
        String functionName = "ruleEnDis" + sourceValuesHelper.sourceIdentifiers() + ruleMetadata.getId();
        buffer.append("function ").append(functionName).append("()\n{");
        Collection<String> coll = request.targetValueIdentifier();
        Iterator<String> iter = coll.iterator();
        String sourceValueIds = sourceValuesHelper.sourceValueIds();
        List<String> ruleElementDTCollDescription = Arrays.asList(sourceValueIds.split(","));
        while (iter.hasNext()) {
            buffer.append("\n var foo = [];\n");
            buffer.append("$j('#").append(sourceValuesHelper.sourceIdentifiers()).append(" :selected').each(function(i, selected){");
            buffer.append("\n foo[i] = $j(selected).val();\n");
            buffer.append(" });\n");
            if (sourceValuesHelper.sourceIdentifiers() != null) {
                Collection<String> collSourceValue = Collections.singleton(sourceValuesHelper.sourceValueIds());
                Iterator<String> iterSourceValue = collSourceValue.iterator();
                String[] str = new String[collSourceValue.size()];
                int ii = 0;

                while (iterSourceValue.hasNext()) {
                    str[ii++] = iterSourceValue.next();
                }
                if (request.anySourceValue()) {
                    buffer.append("if(foo.length>0 && foo[0] != '') {\n"); //anything selected
                } else {
                    buffer.append("\n if(");
                    for (int i = 0; i < str.length; i++) {
                        buffer.append("($j.inArray('").append(str[i]).append("',foo) > -1)");
                        buffer.append(" || ($j.inArray('").append(ruleElementDTCollDescription.get(i)).append("'.replace(/^\\s+|\\s+$/g,''),foo) > -1)");//added for the business rule view
                    }
                    buffer.append("){\n");
                }
                Collection<String> targetIdentifier = request.targetValueIdentifier();
                //For fixing issue where the rule_expresion doesn't contains the comparator = when the source value is Any Source value
                if (request.comparator().equalsIgnoreCase("") && request.anySourceValue()) {
                    ruleMetadata.setLogic("=");
                }

                Iterator<String> iterator = targetIdentifier.iterator();
                if ("Question".equalsIgnoreCase(request.targetType())) {
                    while (iterator.hasNext()) {
                        String targetQuestionIdentifier = iter.next();

                        if (request.ruleFunction().equalsIgnoreCase(ENABLE)) {
                            if ("=".equalsIgnoreCase(request.comparator())) {
                                buffer.append(PG_ENABLE_ELEMENT).append(targetQuestionIdentifier).append(PARANTHESIS);
                            } else {
                                buffer.append(PG_DISABLE_ELEMENT).append(targetQuestionIdentifier).append(PARANTHESIS);
                            }
                        } else {
                            if ("=".equalsIgnoreCase(request.comparator())) {
                                buffer.append(PG_DISABLE_ELEMENT).append(targetQuestionIdentifier).append(PARANTHESIS);
                            } else {
                                buffer.append(PG_ENABLE_ELEMENT).append(targetQuestionIdentifier).append(PARANTHESIS);
                            }
                        } //else
                    } //if
                }//while
                buffer.append(" } else { \n");
                iter = coll.iterator();
                while (iter.hasNext()) {
                    String targetQuestionIdentifier = iter.next();
                    targetQuestionIdentifier = targetQuestionIdentifier.trim();

                    if (request.ruleFunction().equalsIgnoreCase(ENABLE)) {
                        if ("=".equalsIgnoreCase(request.comparator())) {
                            buffer.append(PG_DISABLE_ELEMENT).append(targetQuestionIdentifier).append(PARANTHESIS);
                        } else {
                            buffer.append(PG_ENABLE_ELEMENT).append(targetQuestionIdentifier).append(PARANTHESIS);
                        }
                    } else {
                        if ("=".equalsIgnoreCase(request.comparator())) {
                            buffer.append(PG_ENABLE_ELEMENT).append(targetQuestionIdentifier).append(PARANTHESIS);
                        } else {
                            buffer.append(PG_DISABLE_ELEMENT).append(targetQuestionIdentifier).append(PARANTHESIS);
                        }
                    }
                } //while
                buffer.append(" }");
            } else {
                while (iter.hasNext()) {
                    String targetQuestionIdentifier = iter.next();
                    targetQuestionIdentifier = targetQuestionIdentifier.trim();
                    if (request.ruleFunction().equalsIgnoreCase(ENABLE)) {
                        if ("=".equalsIgnoreCase(request.comparator())) {
                            buffer.append(PG_SUBSECTIONDISABLED).append(targetQuestionIdentifier).append(PARANTHESIS);
                        } else {
                            buffer.append(PG_SUBSECTIONENABLED).append(targetQuestionIdentifier).append(PARANTHESIS);
                        }
                    }
                }
                buffer.append(" } else { \n");
                iter = coll.iterator();
                while (iter.hasNext()) {
                    String targetQuestionIdentifier = iter.next();
                    targetQuestionIdentifier = targetQuestionIdentifier.trim();
                    if (request.ruleFunction().equalsIgnoreCase(ENABLE)) {
                        if ("=".equalsIgnoreCase(request.comparator())) {
                            buffer.append(PG_SUBSECTIONDISABLED).append(targetQuestionIdentifier).append(PARANTHESIS);
                        } else {
                            buffer.append(PG_SUBSECTIONENABLED).append(targetQuestionIdentifier).append(PARANTHESIS);
                        }
                    } else {
                        if ("=".equalsIgnoreCase(request.comparator())) {
                            buffer.append(PG_SUBSECTIONENABLED).append(targetQuestionIdentifier).append(PARANTHESIS);
                        } else {
                            buffer.append(PG_SUBSECTIONDISABLED).append(targetQuestionIdentifier).append(PARANTHESIS);
                        }
                    }
                }
                buffer.append(" }");
            }
        }
        buffer.append("   \n}");
        return new JSFunctionNameHelper(buffer.toString(), functionName + "()");
    }

    private JSFunctionNameHelper requireIfJsFunction(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, WaRuleMetadata ruleMetadata,TargetValuesHelper targetValuesHelper) {
        String functionName = "ruleRequireIf" + request.sourceIdentifier() + ruleMetadata.getId();
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n var foo = [];\n");
        buffer.append("$j('#").append(request.sourceIdentifier()).append(" :selected').each(function(i, selected){");
        buffer.append("\n foo[i] = $j(selected).val();\n");
        buffer.append(" });\n");
        if (request.sourceIdentifier() != null) {

            String sourceIds = sourceValuesHelper.sourceValueIds();

            if (request.anySourceValue()) {
                buffer.append("if(foo.length>0 && foo[0] != '') {\n");
            } else {
                buffer.append("if(foo.length==0) return;\n");
                buffer.append("\n if(");
                buffer.append("($j.inArray('").append(sourceIds).append("',foo) > -1)");
                buffer.append("){\n");
            }
            String secondPart= frameSecondPartOfRequireIf(request,targetValuesHelper);
            buffer.append(secondPart);
        }
        buffer.append("   \n}");
        return new JSFunctionNameHelper(buffer.toString(), functionName);

    }

    private String frameSecondPartOfRequireIf(CreateRuleRequest.ruleRequest request, TargetValuesHelper targetValuesHelper){
        StringBuilder buffer= new StringBuilder();
        String targetIdentifier= targetValuesHelper.targetIdentifier();
        if(Objects.equals(request.comparator(), "=")){
            buffer.append("pgRequireElement('").append(targetIdentifier).append(PARANTHESIS);
        }else{
            buffer.append("pgRequireNotElement('").append(targetIdentifier).append(PARANTHESIS);
        }
        buffer.append(" } else { \n");
        if(Objects.equals(request.comparator(), "=")){
            buffer.append("pgRequireNotElement('").append(targetIdentifier).append(PARANTHESIS);
        }else{
            buffer.append("pgRequireElement('").append(targetIdentifier).append(PARANTHESIS);
        }
        buffer.append(" }");

        return buffer.toString();
    }

    private JSFunctionNameHelper jsForHideAndUnhide(CreateRuleRequest.ruleRequest request, SourceValuesHelper sourceValuesHelper, WaRuleMetadata ruleMetadata){
       String sourceValues= sourceValuesHelper.sourceValueIds();
       List<String> sourceValueIdsList= Arrays.asList(sourceValues.split(","));
       StringBuilder stringBuilder= new StringBuilder();
       String functionName="ruleHideUnh"+request.sourceIdentifier()+ruleMetadata.getId();
       stringBuilder.append("function ").append(functionName).append("()\n{");
       stringBuilder.append(ruleLeftAndRightInvestigation(request,stringBuilder,"",ruleMetadata,sourceValueIdsList,sourceValuesHelper));
       stringBuilder.append(ruleLeftAndRightInvestigation(request,stringBuilder,"_2",ruleMetadata,sourceValueIdsList,sourceValuesHelper));
       stringBuilder.append("   \n}");
       return new JSFunctionNameHelper(stringBuilder.toString(),functionName);
    }

    private StringBuilder ruleLeftAndRightInvestigation(CreateRuleRequest.ruleRequest request,StringBuilder stringBuilder,String suffix,WaRuleMetadata ruleMetadata,List<String> sourceValueIds, SourceValuesHelper sourceValuesHelper){
        String questionIdentifier= request.sourceIdentifier();
        stringBuilder.append("\n var foo").append(suffix).append(" = [];\n");
        stringBuilder.append("$j('#").append(questionIdentifier).append(" :selected').each(function(i, selected){");
        stringBuilder.append("\n foo").append(suffix).append("[i] = $j(selected).val();\n");

        stringBuilder.append(" });\n");
        stringBuilder.append("if(foo").append(suffix).append("=='' && ").append("$j('#").append(questionIdentifier).append("').html()!=null){");//added for the business rule view
        stringBuilder.append("foo").append(suffix).append("[0]=$j('#").append(questionIdentifier).append("').html().replace(/^\\s+|\\s+$/g,'');}");//added for the business rule view
        if(questionIdentifier != null){
            if(request.anySourceValue()){
                stringBuilder.append("if(foo").append(suffix).append(".length>0 && foo").append(suffix).append("[0] != '') {\n");
            }else{
                stringBuilder.append("\n if(");
                for(String sourceId: sourceValueIds){
                    stringBuilder.append("($j.inArray('").append(sourceId).append("',foo").append(suffix).append(") > -1)");
                    stringBuilder.append(" || ($j.inArray('").append(sourceId).append("'.replace(/^\\s+|\\s+$/g,''),foo").append(suffix).append(") > -1");
                    stringBuilder.append(" || indexOfArray(foo,'").append(sourceId).append("')==true)");//added for the business rule view
                    stringBuilder.append(" || ");
                }
                stringBuilder.append("){\n");
            }
        }
        StringBuilder buildJs= framingJSforUnhideAndHide(request,stringBuilder,suffix,ruleMetadata,sourceValueIds,sourceValuesHelper);
        stringBuilder.append(buildJs);
        return stringBuilder;
    }
    private StringBuilder framingJSforUnhideAndHide(CreateRuleRequest.ruleRequest request,StringBuilder stringBuilder,String suffix,WaRuleMetadata ruleMetadata,List<String> sourceValueIds, SourceValuesHelper sourceValuesHelper){
        StringBuilder secondPart= frameSecondPartForUnhideAndHide(request,stringBuilder,suffix);
        StringBuilder subSectionPart= PartForSubSection(request,stringBuilder,suffix,sourceValueIds,sourceValuesHelper);
        stringBuilder.append(secondPart);
        stringBuilder.append(subSectionPart);
        return stringBuilder;
    }
    private StringBuilder frameCommonPartForUnhideAndHide(CreateRuleRequest.ruleRequest request,StringBuilder stringBuilder,String suffix){
        List<String> targetQuestionIdentifiers= request.targetValueIdentifier();
        if("Question".equalsIgnoreCase(request.targetType())){
            for(String targetId: targetQuestionIdentifiers){
                targetId+=suffix;
                if(Objects.equals(request.ruleFunction(),UNHIDE)){
                    if(Objects.equals(request.comparator(), "=")){
                        stringBuilder.append("pgUnhideElement('").append(targetId).append(suffix).append(PARANTHESIS);
                    }else{
                        stringBuilder.append("pgHideElement('").append(targetId).append(suffix).append(PARANTHESIS);
                    }
                }else{
                    if(Objects.equals(request.comparator(), "=")){
                        stringBuilder.append("pgHideElement('").append(targetId).append(suffix).append(PARANTHESIS);
                    }else{
                        stringBuilder.append("pgUnhideElement('").append(targetId).append(suffix).append(PARANTHESIS);
                    }
                }
            }
        }
        return stringBuilder;
    }
    private StringBuilder frameSecondPartForUnhideAndHide(CreateRuleRequest.ruleRequest request,StringBuilder stringBuilder,String suffix){
        StringBuilder firstPart =  frameCommonPartForUnhideAndHide(request,stringBuilder,suffix);
        StringBuilder secondPart= frameThirdPartForUnhideAndHide(request,stringBuilder,suffix);
        stringBuilder.append(firstPart).append(" } else { \n");
        stringBuilder.append(secondPart).append(" }");
        return stringBuilder;
    }

    private StringBuilder frameThirdPartForUnhideAndHide(CreateRuleRequest.ruleRequest request,StringBuilder stringBuilder,String suffix){
        return frameCommonPartForUnhideAndHide(request,stringBuilder,suffix);
    }

    private StringBuilder frameSubSectionPartForUnhideAndHide(CreateRuleRequest.ruleRequest request,StringBuilder stringBuilder,String suffix ){
        List<String> targetQuestionIdentifiers= request.targetValueIdentifier();
        if("Subsection".equalsIgnoreCase(request.targetType())){
            for(String targetId: targetQuestionIdentifiers){
                targetId+=suffix;
                if(Objects.equals(request.ruleFunction(),UNHIDE)){
                    if(Objects.equals(request.comparator(), "=")){
                        stringBuilder.append("pgSubSectionShown('").append(targetId).append(PARANTHESIS);
                    }else{
                        stringBuilder.append("pgSubSectionHidden('").append(targetId).append(PARANTHESIS);
                    }
                }else{
                    if(Objects.equals(request.comparator(), "=")){
                        stringBuilder.append("pgSubSectionHidden('").append(targetId).append(PARANTHESIS);
                    }else{
                        stringBuilder.append("pgSubSectionShown('").append(targetId).append(PARANTHESIS);
                    }
                }
            }
        }
        return stringBuilder;
    }

    private StringBuilder PartForSubSection(CreateRuleRequest.ruleRequest request,StringBuilder stringBuilder,String suffix,List<String> sourceValueIds, SourceValuesHelper sourceValuesHelper){
        StringBuilder firstPart= frameSubSectionPartForUnhideAndHide(request,stringBuilder,suffix);
        StringBuilder secondPart= secondPartForSubSection(request,stringBuilder,suffix);
        stringBuilder.append(firstPart).append(" } else { \n");
        stringBuilder.append(secondPart).append(" }");
        return stringBuilder;
    }

    private StringBuilder secondPartForSubSection(CreateRuleRequest.ruleRequest request,StringBuilder stringBuilder,String suffix){
        return frameSubSectionPartForUnhideAndHide(request,stringBuilder,suffix);
    }
}
