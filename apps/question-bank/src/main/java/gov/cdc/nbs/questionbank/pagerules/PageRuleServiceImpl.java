package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PageRuleServiceImpl implements PageRuleService {
    private static final String DATE_COMPARE = "Date Compare";
    private static final String ELSE = " } else { \n";
    private static final String IF = "\n if(";
    private static final String ARRAY = "($j.inArray('";
    private static final String DISABLE = "Disable";
    private static final String ENABLE = "Enable";
    private static final String REQUIRE_IF = "Require If";
    private static final String HIDE = "Hide";
    private static final String UNHIDE = "Unhide";
    private static final String MUST_BE = "must be";
    private static final String PARANTHESIS = "');\n";
    private static final String DOLLARCLOSING = "$j('#";
    private static final String ANY_SOURCE_VALUE = "( Any Source Value )";
    private static final String PG_ENABLE_ELEMENT = "pgEnableElement('";
    private static final String PG_DISABLE_ELEMENT = "pgDisableElement('";
    private static final String SELECTED = " :selected').each(function(i, selected){";
    private static final String LINESEPERATOR_PARANTHESIS = " });\n";
    private static final String FUNCTION = "function ";
    private static final String ACTION_1 = "()\n{";

    private final WaRuleMetaDataRepository waRuleMetaDataRepository;
    private final RuleCreatedEventProducer ruleCreatedEventProducer;


    public PageRuleServiceImpl(
            WaRuleMetaDataRepository waRuleMetaDataRepository,
            RuleCreatedEventProducer ruleCreatedEventProducer) {
        this.waRuleMetaDataRepository = waRuleMetaDataRepository;
        this.ruleCreatedEventProducer = ruleCreatedEventProducer;
    }

    @Override
    public CreateRuleResponse createPageRule(Long userId, CreateRuleRequest request) {
        WaRuleMetadata waRuleMetadata = setRuleDataValues(userId, request);
        log.info("Saving Rule to DB");
        waRuleMetaDataRepository.save(waRuleMetadata);
        sendRuleEvent(request);
        return new CreateRuleResponse(waRuleMetadata.getId(), "Rule Created Successfully");

    }

    private void sendRuleEvent(CreateRuleRequest ruleRequest) {
        ruleCreatedEventProducer.send(new RuleCreatedEvent(ruleRequest));
    }

    private WaRuleMetadata setRuleDataValues(Long userId, CreateRuleRequest request) {
        WaRuleMetadata ruleMetadata = new WaRuleMetadata();
        RuleData ruleData = createRuleData(request, ruleMetadata);
        ruleMetadata.setRuleCd(request.ruleFunction());
        ruleMetadata.setRuleDescText(request.ruleDescription());
        ruleMetadata.setSourceValues(ruleData.sourceValues());
        ruleMetadata.setLogic(request.comparator());
        ruleMetadata.setSourceQuestionIdentifier(ruleData.sourceIdentifier());
        ruleMetadata.setTargetQuestionIdentifier(ruleData.targetIdentifiers());
        ruleMetadata.setTargetType(request.targetType());
        ruleMetadata.setAddTime(Instant.now());
        ruleMetadata.setAddUserId(userId);
        ruleMetadata.setLastChgTime(Instant.now());
        ruleMetadata.setRecordStatusCd("ACTIVE");
        ruleMetadata.setLastChgUserId(userId);
        ruleMetadata.setRecordStatusTime(Instant.now());
        ruleMetadata.setErrormsgText(ruleData.errorMsgText());
        ruleMetadata.setJsFunction(ruleData.jsFunctionNameHelper().jsFunction());
        ruleMetadata.setJsFunctionName(ruleData.jsFunctionNameHelper().jsFunctionName());
        ruleMetadata.setWaTemplateUid(request.templateUid());
        ruleMetadata.setRuleExpression(ruleData.ruleExpression());

        return ruleMetadata;
    }

    private RuleData createRuleData(
            CreateRuleRequest request,
            WaRuleMetadata ruleMetadata) {
        SourceValuesHelper sourceValuesHelper = sourceValuesHelper(request);
        TargetValuesHelper targetValuesHelper = targetValuesHelper(request);
        RuleExpressionHelper expressionValues = null;

        if (DATE_COMPARE.equals(request.ruleFunction())) {
            expressionValues = dateCompareFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
        }
        if (DISABLE.equals(request.ruleFunction()) || ENABLE.equals(request.ruleFunction())) {
            expressionValues = enableOrDisableFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
        }
        if (HIDE.equals(request.ruleFunction())) {
            expressionValues = hideFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
        }
        if (REQUIRE_IF.equals(request.ruleFunction())) {
            expressionValues = requireIfFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
        }
        if (UNHIDE.equals(request.ruleFunction())) {
            expressionValues = unHideFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
        }
        if (expressionValues != null) {
            return new RuleData(
                    targetValuesHelper.targetIdentifier(),
                    expressionValues.ruleExpression(),
                    expressionValues.errorMessage(),
                    sourceValuesHelper.sourceIdentifiers(),
                    sourceValuesHelper.sourceText(),
                    sourceValuesHelper.sourceValueText(),
                    expressionValues.jsFunctionNameHelper());
        } else {
            throw new RuleException("Error in Creating Rule Expression and Error Message Text", 400);
        }
    }

    private SourceValuesHelper sourceValuesHelper(CreateRuleRequest request) {
        String sourceText = request.sourceText();
        String sourceIdentifier = request.sourceIdentifier();
        List<CreateRuleRequest.SourceValues> sourceValueList = request.sourceValue();
        String sourceIds = null;
        String sourceValueText = null;
        if (request.sourceValue() != null) {
            for (CreateRuleRequest.SourceValues sourceValues : sourceValueList) {
                sourceIds = String.join(",", sourceValues.sourceValueId());
                sourceValueText = String.join(",", sourceValues.sourceValueText());
            }
        }
        return new SourceValuesHelper(sourceIds, sourceValueText, sourceText, sourceIdentifier);
    }

    private TargetValuesHelper targetValuesHelper(CreateRuleRequest request) {
        List<String> targetTextList = request.targetValueText();
        List<String> targetValueIdentifierList = request.targetValueIdentifier();
        String targetIdentifier = String.join(",", targetValueIdentifierList);

        return new TargetValuesHelper(targetIdentifier, targetTextList);
    }

    public RuleExpressionHelper dateCompareFunction(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            TargetValuesHelper targetValuesHelper,
            WaRuleMetadata ruleMetadata) {
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String ruleExpression = sourceIdentifier.concat(" ")
                .concat(request.comparator())
                .concat(" ")
                .concat("^ DT")
                .concat(" ")
                .concat("( " + targetIdentifier + " )");
        for (String targetText : targetTextList) {
            String errMsg = sourceText.concat(" ")
                    .concat(MUST_BE)
                    .concat(" ")
                    .concat(request.comparator())
                    .concat(" ")
                    .concat(targetText);
            errorMessageList.add(errMsg);
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper = jsForDateCompare(
                request,
                sourceValuesHelper,
                targetValuesHelper,
                ruleMetadata);

        return new RuleExpressionHelper(errorMessageText, ruleExpression,
                new JSFunctionNameHelper(jsFunctionNameHelper.jsFunction(), jsFunctionNameHelper.jsFunctionName()));
    }

    public RuleExpressionHelper enableOrDisableFunction(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            TargetValuesHelper targetValuesHelper,
            WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String indicator = ENABLE.equals(request.ruleFunction()) ? "E" : "D";

        String commonErrMsgForAnySource = sourceText.concat(" ")
                .concat(" ")
                .concat(MUST_BE)
                .concat(" ")
                .concat(ANY_SOURCE_VALUE)
                .concat(" ");
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ")
                .concat("( )")
                .concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ")
                .concat("(" + sourceIds + ")")
                .concat(" ");

        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression = commonRuleExpressionForAnySource.concat("^ ")
                    .concat(indicator)
                    .concat(" ")
                    .concat("( " + targetIdentifier + " )");
            for (String targetText : targetTextList) {
                String errMsg = commonErrMsgForAnySource.concat(targetText);
                errorMessageList.add(errMsg);
            }
        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator())
                    .concat(" ")
                    .concat("^ ")
                    .concat(indicator)
                    .concat(" ")
                    .concat("(" + targetIdentifier + ")");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ")
                        .concat(request.comparator())
                        .concat(" ")
                        .concat(MUST_BE)
                        .concat(" ")
                        .concat("(" + sourceValueText + ")")
                        .concat(" ")
                        .concat(targetText);
                errorMessageList.add(errMsg);
            }
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper = jsForEnableAndDisable(request, sourceValuesHelper, ruleMetadata);
        return new RuleExpressionHelper(
                errorMessageText,
                ruleExpression,
                new JSFunctionNameHelper(jsFunctionNameHelper.jsFunction(), jsFunctionNameHelper.jsFunctionName()));
    }

    public RuleExpressionHelper hideFunction(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            TargetValuesHelper targetValuesHelper,
            WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String commonErrMsgForAnySource = sourceText.concat(" ")
                .concat(" ")
                .concat(MUST_BE)
                .concat(" ")
                .concat(ANY_SOURCE_VALUE)
                .concat(" ");
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ")
                .concat("( )")
                .concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ")
                .concat("(" + sourceIds + ")")
                .concat(" ");

        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression =
                    commonRuleExpressionForAnySource.concat("^ H")
                            .concat(" ")
                            .concat("( " + targetIdentifier + " )");
            for (String targetText : targetTextList) {
                String errMsg = commonErrMsgForAnySource.concat(targetText);
                errorMessageList.add(errMsg);
            }
        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator())
                    .concat(" ")
                    .concat("^ H")
                    .concat(" ")
                    .concat("(" + targetIdentifier + ")");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ")
                        .concat(request.comparator())
                        .concat(" ")
                        .concat(MUST_BE)
                        .concat(" ")
                        .concat("(" + sourceValueText + ")")
                        .concat(" ")
                        .concat(targetText);
                errorMessageList.add(errMsg);
            }
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper = jsForHideAndUnhide(request, sourceValuesHelper, ruleMetadata);
        return new RuleExpressionHelper(errorMessageText, ruleExpression,
                new JSFunctionNameHelper(jsFunctionNameHelper.jsFunction(), jsFunctionNameHelper.jsFunctionName()));
    }

    public RuleExpressionHelper requireIfFunction(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            TargetValuesHelper targetValuesHelper,
            WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ")
                .concat("( )")
                .concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ")
                .concat("(" + sourceIds + ")")
                .concat(" ");

        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression =
                    commonRuleExpressionForAnySource.concat("^ R")
                            .concat(" ")
                            .concat("( " + targetIdentifier + " )");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ")
                        .concat(" ")
                        .concat(" ")
                        .concat(ANY_SOURCE_VALUE)
                        .concat(" ")
                        .concat(targetText)
                        .concat(" ")
                        .concat("is required");
                errorMessageList.add(errMsg);
            }
        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator())
                    .concat(" ")
                    .concat("^ R")
                    .concat(" ")
                    .concat("(" + targetIdentifier + ")");
            for (String targetText : targetTextList) {
                String errMsg = sourceText.concat(" ")
                        .concat(request.comparator())
                        .concat(" ")
                        .concat("(" + sourceValueText + ")")
                        .concat(" ")
                        .concat(targetText)
                        .concat(" ")
                        .concat("is required");
                errorMessageList.add(errMsg);
            }
        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper =
                requireIfJsFunction(request, sourceValuesHelper, ruleMetadata, targetValuesHelper);
        return new RuleExpressionHelper(errorMessageText, ruleExpression,
                new JSFunctionNameHelper(jsFunctionNameHelper.jsFunction(), jsFunctionNameHelper.jsFunctionName()));
    }

    public RuleExpressionHelper unHideFunction(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            TargetValuesHelper targetValuesHelper,
            WaRuleMetadata ruleMetadata) {
        String ruleExpression;
        List<String> errorMessageList = new ArrayList<>();
        String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
        String sourceText = sourceValuesHelper.sourceText();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        List<String> targetTextList = targetValuesHelper.targetTextList();
        String sourceIds = sourceValuesHelper.sourceValueIds();
        String sourceValueText = sourceValuesHelper.sourceValueText();
        String commonRuleExpressionForAnySource = sourceIdentifier.concat(" ")
                .concat("( )")
                .concat(" ");
        String commonRuleExpForSourceValue = sourceIdentifier.concat(" ")
                .concat("(" + sourceIds + ")")
                .concat(" ");
        String targetText = String.join(",", targetTextList);
        if (request.anySourceValue() && Objects.equals(request.comparator(), "=")) {
            ruleExpression = commonRuleExpressionForAnySource.concat("^ S")
                    .concat(" ")
                    .concat("( " + targetIdentifier + " )");
            String errMsg = sourceText.concat(" ")
                    .concat(" ")
                    .concat(MUST_BE)
                    .concat(" ")
                    .concat("(" + ANY_SOURCE_VALUE + ")")
                    .concat(" ")
                    .concat(targetText);
            errorMessageList.add(errMsg);

        } else {
            ruleExpression = commonRuleExpForSourceValue.concat(request.comparator())
                    .concat(" ")
                    .concat("^ S")
                    .concat(" ")
                    .concat("(" + targetIdentifier + ")");
            String errMsg = sourceText.concat(" ")
                    .concat(request.comparator())
                    .concat(MUST_BE)
                    .concat("(" + sourceValueText + ")")
                    .concat(" ")
                    .concat(targetText);
            errorMessageList.add(errMsg);

        }
        String errorMessageText = String.join(",", errorMessageList);
        JSFunctionNameHelper jsFunctionNameHelper = jsForHideAndUnhide(request, sourceValuesHelper, ruleMetadata);
        return new RuleExpressionHelper(errorMessageText, ruleExpression,
                new JSFunctionNameHelper(jsFunctionNameHelper.jsFunction(), jsFunctionNameHelper.jsFunctionName()));
    }

    public JSFunctionNameHelper jsForDateCompare(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            TargetValuesHelper targetValuesHelper,
            WaRuleMetadata ruleMetadata) {
        StringBuilder stringBuffer = new StringBuilder();
        StringBuilder firstSB = new StringBuilder();
        StringBuilder secondSB = new StringBuilder();
        String sourceQuestionIdentifier = request.sourceIdentifier();
        String jsFunctionName = "ruleDComp" + sourceQuestionIdentifier + ruleMetadata.getId();
        stringBuffer.append(FUNCTION + jsFunctionName + "() {\n");
        stringBuffer.append("    var i = 0;\n    var errorElts = new Array(); \n    var errorMsgs = new Array(); \n");
        firstSB.append("\n if ((getElementByIdOrByName(\"").append(sourceValuesHelper.sourceIdentifiers())
                .append("\").value)==''){ \n return {elements : errorElts, labels : errorMsgs}; }");
        secondSB.append("\n var sourceStr =getElementByIdOrByName(\"").append(sourceValuesHelper.sourceIdentifiers())
                .append("\").value;");
        secondSB.append(
                "\n var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);");
        secondSB.append("\n var targetElt;\n var targetStr = ''; \n var targetDate = '';");
        Collection<String> coll = request.targetValueIdentifier();
        for (String targetQuestionIdentifier : coll) {
            //check for null just in case the target got deleted or is not visible except for edit
            secondSB.append("\n targetStr =getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim())
                    .append("\") == null ? \"\" :getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim())
                    .append("\").value;");
            secondSB.append("\n if (targetStr!=\"\") {");
            secondSB.append(
                    "\n    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);");
            secondSB.append("\n if (!(srcDate ");
            secondSB.append(request.comparator());
            secondSB.append(" targetDate)) {");
            secondSB.append("\n var srcDateEle=getElementByIdOrByName(\"")
                    .append(sourceValuesHelper.sourceIdentifiers()).append("\");");
            secondSB.append("\n var targetDateEle=getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim())
                    .append("\");");
            try {
                secondSB.append("\n var srca2str=buildErrorAnchorLink(srcDateEle," + "\"");
                secondSB.append(
                        sourceValuesHelper.sourceText().substring(0, sourceValuesHelper.sourceText().indexOf("("))
                                .trim())
                        .append("\");");
            } catch (Exception e) {
                secondSB.append(
                        sourceValuesHelper.sourceText().trim()).append("\");");

            }
            secondSB.append("\n var targeta2str=buildErrorAnchorLink(targetDateEle,\"")
                    .append(targetValuesHelper.targetTextList().get(0)).append("\");");
            secondSB.append("\n    errorMsgs[i]=srca2str + \" must be ").append(request.comparator())
                    .append(" \" + targeta2str; ");
            secondSB.append("\n    colorElementLabelRed(srcDateEle); ");
            secondSB.append("\n    colorElementLabelRed(targetDateEle); \n");

            secondSB.append("errorElts[i++]=getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim())
                    .append("\"); \n");
            secondSB.append("}\n  }");
        }
        stringBuffer.append(firstSB);
        stringBuffer.append(secondSB);
        stringBuffer.append("\n return {elements : errorElts, labels : errorMsgs}\n}");
        return new JSFunctionNameHelper(stringBuffer.toString(), jsFunctionName + "()");
    }

    public JSFunctionNameHelper jsForEnableAndDisable(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            WaRuleMetadata ruleMetadata) {
        StringBuilder builder = new StringBuilder();
        String functionName = "ruleEnDis" + sourceValuesHelper.sourceIdentifiers() + ruleMetadata.getId();
        builder.append(FUNCTION).append(functionName).append(ACTION_1);
        builder.append("\n var foo = [];\n");
        builder.append(DOLLARCLOSING).append(request.sourceIdentifier()).append(SELECTED);
        builder.append("\n foo[i] = $j(selected).val();\n");
        builder.append(LINESEPERATOR_PARANTHESIS);

        firstPartForEnDs(request, sourceValuesHelper, builder);
        String sourceValues = sourceValuesHelper.sourceValueIds();
        if (sourceValues != null) {
            List<String> sourceValueList = Arrays.asList(sourceValues.split(","));
            for (int i = 0; i < sourceValueList.size(); i++) {
                builder = thirdPartForEnDs(request, builder);
            }
        }

        builder.append("\n}");

        return new JSFunctionNameHelper(builder.toString(), functionName);
    }

    private StringBuilder firstPartForEnDs(CreateRuleRequest request, SourceValuesHelper sourceValuesHelper,
            StringBuilder stringBuilder) {
        String sourceValues = sourceValuesHelper.sourceValueIds();
        if (sourceValues == null) {
            log.info("Any SourceValue is true for this request");
            sourceValues = ",";
        }
        List<String> sourceValueList = Arrays.asList(sourceValues.split(","));
        if (request.anySourceValue()) {
            stringBuilder.append("if(foo.length>0 && foo[0] != '') {\n"); //anything selected
        } else {
            stringBuilder.append(IF);
            for (int i = 0; i < sourceValueList.size(); i++) {
                String sourceId = sourceValueList.get(i);
                stringBuilder.append(ARRAY).append(sourceId).append("',foo) > -1)");
                stringBuilder.append(" || ($j.inArray('").append(sourceId)
                        .append("'.replace(/^\\s+|\\s+$/g,''),foo) > -1)");//added for the business rule view
                try {
                    sourceValueList.get(i + 1);
                    stringBuilder.append("||");
                } catch (Exception e) {
                    break;
                }

            }
            stringBuilder.append("){\n");
        }
        return stringBuilder;

    }

    private StringBuilder commonElementPartForEnDs(CreateRuleRequest request, StringBuilder stringBuilder) {
        List<String> targetQuestionIdentifiers = request.targetValueIdentifier();

        if ("Question".equalsIgnoreCase(request.targetType())) {
            for (String targetId : targetQuestionIdentifiers) {
                if (ENABLE.equalsIgnoreCase(request.ruleFunction()) && Objects.equals(request.comparator(), "=")) {
                    stringBuilder.append(PG_ENABLE_ELEMENT).append(targetId).append(PARANTHESIS).append(" }");
                    stringBuilder.append(" else { \n").append(PG_DISABLE_ELEMENT).append(targetId).append(PARANTHESIS)
                            .append(" }");
                }
                if (!Objects.equals(ENABLE, request.ruleFunction()) && Objects.equals(request.comparator(), "=")) {
                    stringBuilder.append(PG_DISABLE_ELEMENT).append(targetId).append(PARANTHESIS);
                    stringBuilder.append(" else { \n").append(PG_ENABLE_ELEMENT).append(targetId).append(PARANTHESIS)
                            .append(" }");
                }
            }
        }
        return stringBuilder;
    }


    private StringBuilder thirdPartForEnDs(CreateRuleRequest request, StringBuilder stringBuilder) {
        return commonElementPartForEnDs(request, stringBuilder);
    }

    public JSFunctionNameHelper requireIfJsFunction(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            WaRuleMetadata ruleMetadata,
            TargetValuesHelper targetValuesHelper) {
        String functionName = "ruleRequireIf" + request.sourceIdentifier() + ruleMetadata.getId();
        StringBuilder buffer = new StringBuilder();
        buffer.append(FUNCTION + functionName + ACTION_1);
        buffer.append("\n var foo = [];\n");
        buffer.append(DOLLARCLOSING).append(request.sourceIdentifier()).append(SELECTED);
        buffer.append("\n foo[i] = $j(selected).val();\n");
        buffer.append(LINESEPERATOR_PARANTHESIS);
        if (request.sourceIdentifier() != null) {

            String sourceValueText = sourceValuesHelper.sourceValueText();

            if (request.anySourceValue()) {
                buffer.append("if(foo.length>0 && foo[0] != '') {\n");
            } else {
                buffer.append("if(foo.length==0) return;\n");
                buffer.append(IF);
                buffer.append(ARRAY).append(sourceValueText.toUpperCase(), 0, 3).append("',foo) > -1)");
                buffer.append("){\n");
            }
            String secondPart = frameSecondPartOfRequireIf(request, targetValuesHelper);
            buffer.append(secondPart);
        }
        buffer.append("   \n}");
        return new JSFunctionNameHelper(buffer.toString(), functionName);

    }

    private String frameSecondPartOfRequireIf(CreateRuleRequest request, TargetValuesHelper targetValuesHelper) {
        StringBuilder buffer = new StringBuilder();
        String targetIdentifier = targetValuesHelper.targetIdentifier();
        if (Objects.equals(request.comparator(), "=")) {
            buffer.append("pgRequireElement('").append(targetIdentifier).append(PARANTHESIS);
        } else {
            buffer.append("pgRequireNotElement('").append(targetIdentifier).append(PARANTHESIS);
        }
        buffer.append(ELSE);
        if (Objects.equals(request.comparator(), "=")) {
            buffer.append("pgRequireNotElement('").append(targetIdentifier).append(PARANTHESIS);
        } else {
            buffer.append("pgRequireElement('").append(targetIdentifier).append(PARANTHESIS);
        }
        buffer.append(" }");

        return buffer.toString();
    }

    public JSFunctionNameHelper jsForHideAndUnhide(
            CreateRuleRequest request,
            SourceValuesHelper sourceValuesHelper,
            WaRuleMetadata ruleMetadata) {
        String sourceText = sourceValuesHelper.sourceValueText();
        if (sourceText == null) {
            log.info("Any SourceValue is true for this request");
            sourceText = ",";
        }
        List<String> sourceValueTextList = Arrays.asList(sourceText.split(","));
        StringBuilder stringBuilder = new StringBuilder();
        String functionName = "ruleHideUnh" + request.sourceIdentifier() + ruleMetadata.getId();
        stringBuilder.append(FUNCTION).append(functionName).append(ACTION_1);
        ruleLeftAndRightInvestigation(request, stringBuilder, "", sourceValueTextList);
        ruleLeftAndRightInvestigation(request, stringBuilder, "_2", sourceValueTextList);
        stringBuilder.append("   \n}");
        return new JSFunctionNameHelper(stringBuilder.toString(), functionName);
    }

    private StringBuilder ruleLeftAndRightInvestigation(
            CreateRuleRequest request,
            StringBuilder stringBuilder,
            String suffix,
            List<String> sourceValueTextList) {
        String questionIdentifier = request.sourceIdentifier();
        stringBuilder.append("\n var foo").append(suffix).append(" = [];\n");
        stringBuilder.append(DOLLARCLOSING).append(questionIdentifier).append(suffix).append(SELECTED);
        stringBuilder.append("\n foo").append(suffix).append("[i] = $j(selected).val();\n");

        stringBuilder.append(LINESEPERATOR_PARANTHESIS);
        stringBuilder.append("if(foo").append(suffix).append("=='' && ").append(DOLLARCLOSING)
                .append(questionIdentifier).append(suffix).append("').html()!=null){");//added for the business rule view
        stringBuilder.append("foo").append(suffix).append("[0]=$j('#").append(questionIdentifier).append(suffix)
                .append("').html().replace(/^\\s+|\\s+$/g,'');}");//added for the business rule view
        if (questionIdentifier != null) {
            if (request.anySourceValue()) {
                stringBuilder.append("if(foo").append(suffix).append(".length>0 && foo").append(suffix)
                        .append("[0] != '') {\n");
            } else {
                stringBuilder.append(IF);
                int i = 0;
                for (String sourcetext : sourceValueTextList) {
                    if (i != 0) {
                        stringBuilder.append(" || ");
                    }
                    i++;
                    stringBuilder.append(ARRAY).append(sourcetext.charAt(0)).append("',foo").append(suffix)
                            .append(") > -1)");
                    stringBuilder.append(" || ($j.inArray('").append(sourcetext)
                            .append("'.replace(/^\\s+|\\s+$/g,''),foo").append(suffix).append(") > -1");
                    stringBuilder.append(" || indexOfArray(foo,'").append(sourcetext).append("')==true)");
                    //added for the business rule view
                }
                stringBuilder.append("){\n");
            }
        }
        framingJSforUnhideAndHide(request, stringBuilder, suffix);
        return stringBuilder;
    }

    private StringBuilder framingJSforUnhideAndHide(
            CreateRuleRequest request,
            StringBuilder stringBuilder,
            String suffix) {
        frameSecondPartForUnhideAndHide(request, stringBuilder, suffix);
        partForSubSection(request, stringBuilder, suffix);
        return stringBuilder;
    }

    private StringBuilder frameCommonPartForUnhideAndHide(
            CreateRuleRequest request,
            StringBuilder stringBuilder,
            String suffix) {
        List<String> targetQuestionIdentifiers = request.targetValueIdentifier();
        if ("Question".equalsIgnoreCase(request.targetType())) {
            for (String targetId : targetQuestionIdentifiers) {
                targetId += suffix;
                if (Objects.equals(request.ruleFunction(), UNHIDE) && Objects.equals(request.comparator(), "=")) {
                    stringBuilder.append("pgUnhideElement('").append(targetId).append(PARANTHESIS);
                    stringBuilder.append(ELSE);
                    stringBuilder.append("pgHideElement('").append(targetId).append(PARANTHESIS);
                } else {
                    stringBuilder.append("pgHideElement('").append(targetId).append(PARANTHESIS);
                    stringBuilder.append(ELSE);
                    stringBuilder.append("pgUnhideElement('").append(targetId).append(PARANTHESIS);
                }
            }
        }
        return stringBuilder;
    }

    private StringBuilder frameSecondPartForUnhideAndHide(CreateRuleRequest request,
            StringBuilder stringBuilder, String suffix) {
        frameCommonPartForUnhideAndHide(request, stringBuilder, suffix);
        stringBuilder.append(" }");
        return stringBuilder;
    }

    private StringBuilder frameSubSectionPartForUnhideAndHide(CreateRuleRequest request,
            StringBuilder stringBuilder, String suffix) {
        return nestedSubSection(request, stringBuilder, suffix);
    }

    private StringBuilder nestedSubSection(
            CreateRuleRequest request,
            StringBuilder stringBuilder,
            String suffix) {
        List<String> targetQuestionIdentifiers = request.targetValueIdentifier();
        if ("Subsection".equalsIgnoreCase(request.targetType())) {
            for (String targetId : targetQuestionIdentifiers) {
                targetId += suffix;
                if (Objects.equals(request.ruleFunction(), UNHIDE) && Objects.equals(request.comparator(), "=")) {
                    stringBuilder.append("pgSubSectionShown('").append(targetId).append(PARANTHESIS);
                } else {
                    stringBuilder.append("pgSubSectionHidden('").append(targetId).append(PARANTHESIS);
                }
                if (!Objects.equals(request.ruleFunction(), UNHIDE) && Objects.equals(request.comparator(), "=")) {
                    stringBuilder.append("pgSubSectionHidden('").append(targetId).append(PARANTHESIS);
                } else {
                    stringBuilder.append("pgSubSectionShown('").append(targetId).append(PARANTHESIS);
                }
            }
        }
        return stringBuilder;
    }

    private StringBuilder partForSubSection(CreateRuleRequest request, StringBuilder stringBuilder,
            String suffix) {
        stringBuilder = frameSubSectionPartForUnhideAndHide(request, stringBuilder, suffix);
        return stringBuilder;
    }

    @Override
    public CreateRuleResponse deletePageRule(Long ruleId) {
        waRuleMetaDataRepository.deleteById(ruleId);
        return new CreateRuleResponse(ruleId, "Rule Successfully Deleted");
    }

    @Override
    public CreateRuleResponse updatePageRule(Long ruleId, CreateRuleRequest request, Long userId) {
        boolean isPresent = waRuleMetaDataRepository.existsById(ruleId);
        if (!isPresent) {
            return new CreateRuleResponse(ruleId, "RuleId Not Found");
        } else {
            WaRuleMetadata waRuleMetadata = waRuleMetaDataRepository.getReferenceById(ruleId);
            RuleData ruleData = createRuleData(request, waRuleMetadata);
            WaRuleMetadata updatedValues = setUpdatedValues(ruleData, waRuleMetadata, request, userId);
            waRuleMetaDataRepository.save(updatedValues);
            return new CreateRuleResponse(updatedValues.getId(), "Rule Successfully Updated");
        }
    }

    private WaRuleMetadata setUpdatedValues(
            RuleData ruleData,
            WaRuleMetadata ruleMetadata,
            CreateRuleRequest request,
            Long userId) {
        Instant now = Instant.now();
        ruleMetadata.setRuleCd(request.ruleFunction());
        ruleMetadata.setLogic(request.comparator());
        ruleMetadata.setJsFunction(ruleData.jsFunctionNameHelper().jsFunction());
        ruleMetadata.setSourceValues(ruleData.sourceValues());
        ruleMetadata.setErrormsgText(ruleData.errorMsgText());
        ruleMetadata.setSourceQuestionIdentifier(ruleData.sourceIdentifier());
        ruleMetadata.setTargetQuestionIdentifier(ruleData.targetIdentifiers());
        ruleMetadata.setTargetType(request.targetType());
        ruleMetadata.setAddTime(now);
        ruleMetadata.setAddUserId(userId);
        ruleMetadata.setLastChgTime(now);
        ruleMetadata.setRecordStatusCd("ACTIVE");
        ruleMetadata.setLastChgUserId(userId);
        ruleMetadata.setRecordStatusTime(now);
        ruleMetadata.setErrormsgText(ruleData.errorMsgText());
        ruleMetadata.setJsFunction(ruleData.jsFunctionNameHelper().jsFunction());
        ruleMetadata.setJsFunctionName(ruleData.jsFunctionNameHelper().jsFunctionName());
        ruleMetadata.setWaTemplateUid(request.templateUid());
        ruleMetadata.setRuleExpression(ruleData.ruleExpression());
        ruleMetadata.setId(ruleMetadata.getId());

        return ruleMetadata;
    }

    @Override
    public ViewRuleResponse getRuleResponse(Long ruleId) {
        WaRuleMetadata ruleMetadata = waRuleMetaDataRepository.getReferenceById(ruleId);
        List<String> sourceValues = new ArrayList<>();
        List<String> targetValues = new ArrayList<>();
        if (ruleMetadata.getSourceValues() == null || ruleMetadata.getTargetQuestionIdentifier() == null) {
            sourceValues.add(null);
            targetValues.add(null);
        } else {
            String[] sourceValue = ruleMetadata.getSourceValues().split(",");
            sourceValues = Arrays.asList(sourceValue);
            String[] targetValue = ruleMetadata.getTargetQuestionIdentifier().split(",");
            targetValues = Arrays.asList(targetValue);
        }
        return new ViewRuleResponse(ruleId, ruleMetadata.getWaTemplateUid(), ruleMetadata.getRuleCd(),
                ruleMetadata.getRuleDescText(), ruleMetadata.getSourceQuestionIdentifier(), sourceValues,
                ruleMetadata.getLogic(), ruleMetadata.getTargetType(), ruleMetadata.getErrormsgText(), targetValues);
    }

    @Override
    public Page<ViewRuleResponse> getAllPageRule(Pageable pageRequest) {
        Page<WaRuleMetadata> ruleMetadataPage =waRuleMetaDataRepository.findAll(pageRequest);

        List<ViewRuleResponse> ruleMetadata =
                ruleMetadataPage.getContent().stream().map(rule ->new ViewRuleResponse(rule.getId(), rule.getWaTemplateUid(), rule.getRuleCd(),
                rule.getRuleDescText(), rule.getSourceQuestionIdentifier(), buildSourceTargetValues(rule,true),
                rule.getLogic(), rule.getTargetType(), rule.getErrormsgText(),
                        buildSourceTargetValues(rule,false))).toList();
        return new PageImpl<>(ruleMetadata,ruleMetadataPage.getPageable(),ruleMetadataPage.getTotalElements());

    }

    private List<String> buildSourceTargetValues(WaRuleMetadata ruleMetadata, boolean isSource) {

        List<String> sourceValues = new ArrayList<>();
        List<String> targetValues = new ArrayList<>();
        if(isSource){
            if (ruleMetadata.getSourceValues() == null || ruleMetadata.getTargetQuestionIdentifier() == null) {
                return  sourceValues;
            } else {
               return Arrays.stream(ruleMetadata.getSourceValues().split(",")).toList();
            }
        }else{
            if (ruleMetadata.getSourceValues() == null || ruleMetadata.getTargetQuestionIdentifier() == null) {
                return targetValues;
            } else {
                return Arrays.stream(ruleMetadata.getTargetQuestionIdentifier().split(",")).toList();
            }

        }

    }
}
