package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PageRuleService {
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


  public PageRuleService(
      WaRuleMetaDataRepository waRuleMetaDataRepository) {
    this.waRuleMetaDataRepository = waRuleMetaDataRepository;
  }

  RuleData createRuleData(
      RuleRequest request,
      WaRuleMetadata ruleMetadata) {
    SourceValuesHelper sourceValuesHelper = sourceValuesHelper(request);
    TargetValuesHelper targetValuesHelper = targetValuesHelper(request);
    RuleExpressionHelper expressionValues = null;

    if (DATE_COMPARE.equals(request.ruleFunction().getValue())) {
      expressionValues = dateCompareFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
    }
    if (HIDE.equals(request.ruleFunction().getValue())) {
      expressionValues = hideFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
    }
    if (REQUIRE_IF.equals(request.ruleFunction().getValue())) {
      expressionValues = requireIfFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
    }
    if (UNHIDE.equals(request.ruleFunction().getValue())) {
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
      throw new RuleException("Error in Creating Rule Expression and Error Message Text");
    }
  }

  private SourceValuesHelper sourceValuesHelper(RuleRequest request) {
    String sourceText = request.sourceText();
    String sourceIdentifier = request.sourceIdentifier();
    List<Rule.SourceValue> sourceValues = request.sourceValues();
    String sourceIds = null;
    String sourceValueText = null;
    if (sourceValues != null) {
      sourceIds = sourceValues.stream().map(Rule.SourceValue::id).collect(Collectors.joining(","));
      sourceValueText = sourceValues.stream().map(Rule.SourceValue::text).collect(Collectors.joining(","));
    }
    return new SourceValuesHelper(sourceIds, sourceValueText, sourceText, sourceIdentifier);
  }

  private TargetValuesHelper targetValuesHelper(RuleRequest request) {
    List<String> targetTextList = request.targetValueText();
    List<String> targetValueIdentifierList = request.targetIdentifiers();
    String targetIdentifier = String.join(",", targetValueIdentifierList);

    return new TargetValuesHelper(targetIdentifier, targetTextList);
  }

  public RuleExpressionHelper dateCompareFunction(
      RuleRequest request,
      SourceValuesHelper sourceValuesHelper,
      TargetValuesHelper targetValuesHelper,
      WaRuleMetadata ruleMetadata) {
    List<String> errorMessageList = new ArrayList<>();
    String sourceIdentifier = sourceValuesHelper.sourceIdentifiers();
    String sourceText = sourceValuesHelper.sourceText();
    String targetIdentifier = targetValuesHelper.targetIdentifier();
    List<String> targetTextList = targetValuesHelper.targetTextList();
    String ruleExpression = sourceIdentifier.concat(" ")
        .concat(request.comparator().getValue())
        .concat(" ")
        .concat("^ DT")
        .concat(" ")
        .concat("( " + targetIdentifier + " )");
    for (String targetText : targetTextList) {
      String errMsg = sourceText.concat(" ")
          .concat(MUST_BE)
          .concat(" ")
          .concat(request.comparator().getValue())
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



  public RuleExpressionHelper hideFunction(
      RuleRequest request,
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

    if (request.anySourceValue() && Objects.equals(request.comparator().getValue(), "=")) {
      ruleExpression =
          commonRuleExpressionForAnySource.concat("^ H")
              .concat(" ")
              .concat("( " + targetIdentifier + " )");
      for (String targetText : targetTextList) {
        String errMsg = commonErrMsgForAnySource.concat(targetText);
        errorMessageList.add(errMsg);
      }
    } else {
      ruleExpression = commonRuleExpForSourceValue.concat(request.comparator().getValue())
          .concat(" ")
          .concat("^ H")
          .concat(" ")
          .concat("(" + targetIdentifier + ")");
      for (String targetText : targetTextList) {
        String errMsg = sourceText.concat(" ")
            .concat(request.comparator().getValue())
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
      RuleRequest request,
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

    if (request.anySourceValue() && Objects.equals(request.comparator().getValue(), "=")) {
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
      ruleExpression = commonRuleExpForSourceValue.concat(request.comparator().getValue())
          .concat(" ")
          .concat("^ R")
          .concat(" ")
          .concat("(" + targetIdentifier + ")");
      for (String targetText : targetTextList) {
        String errMsg = sourceText.concat(" ")
            .concat(request.comparator().getValue())
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
      RuleRequest request,
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
    if (request.anySourceValue() && Objects.equals(request.comparator().getValue(), "=")) {
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
      ruleExpression = commonRuleExpForSourceValue.concat(request.comparator().getValue())
          .concat(" ")
          .concat("^ S")
          .concat(" ")
          .concat("(" + targetIdentifier + ")");
      String errMsg = sourceText.concat(" ")
          .concat(request.comparator().getValue())
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
      RuleRequest request,
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
    Collection<String> coll = request.targetIdentifiers();
    for (String targetQuestionIdentifier : coll) {
      //check for null just in case the target got deleted or is not visible except for edit
      secondSB.append("\n targetStr =getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim())
          .append("\") == null ? \"\" :getElementByIdOrByName(\"").append(targetQuestionIdentifier.trim())
          .append("\").value;");
      secondSB.append("\n if (targetStr!=\"\") {");
      secondSB.append(
          "\n    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);");
      secondSB.append("\n if (!(srcDate ");
      secondSB.append(request.comparator().getValue());
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
      secondSB.append("\n    errorMsgs[i]=srca2str + \" must be ").append(request.comparator().getValue())
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



  private StringBuilder commonElementPartForEnDs(RuleRequest request, StringBuilder stringBuilder) {
    List<String> targetQuestionIdentifiers = request.targetIdentifiers();

    if ("Question".equalsIgnoreCase(request.targetType().toString())) {
      for (String targetId : targetQuestionIdentifiers) {
        if (ENABLE.equalsIgnoreCase(request.ruleFunction().toString())
            && Objects.equals(request.comparator().getValue(),
                "=")) {
          stringBuilder.append(PG_ENABLE_ELEMENT).append(targetId).append(PARANTHESIS).append(" }");
          stringBuilder.append(" else { \n").append(PG_DISABLE_ELEMENT).append(targetId).append(PARANTHESIS)
              .append(" }");
        }
        if (!Objects.equals(ENABLE, request.ruleFunction()) && Objects.equals(request.comparator().getValue(), "=")) {
          stringBuilder.append(PG_DISABLE_ELEMENT).append(targetId).append(PARANTHESIS);
          stringBuilder.append(" else { \n").append(PG_ENABLE_ELEMENT).append(targetId).append(PARANTHESIS)
              .append(" }");
        }
      }
    }
    return stringBuilder;
  }



  public JSFunctionNameHelper requireIfJsFunction(
      RuleRequest request,
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

  private String frameSecondPartOfRequireIf(RuleRequest request, TargetValuesHelper targetValuesHelper) {
    StringBuilder buffer = new StringBuilder();
    String targetIdentifier = targetValuesHelper.targetIdentifier();
    if (Objects.equals(request.comparator().getValue(), "=")) {
      buffer.append("pgRequireElement('").append(targetIdentifier).append(PARANTHESIS);
    } else {
      buffer.append("pgRequireNotElement('").append(targetIdentifier).append(PARANTHESIS);
    }
    buffer.append(ELSE);
    if (Objects.equals(request.comparator().getValue(), "=")) {
      buffer.append("pgRequireNotElement('").append(targetIdentifier).append(PARANTHESIS);
    } else {
      buffer.append("pgRequireElement('").append(targetIdentifier).append(PARANTHESIS);
    }
    buffer.append(" }");

    return buffer.toString();
  }

  public JSFunctionNameHelper jsForHideAndUnhide(
      RuleRequest request,
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
      RuleRequest request,
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
      RuleRequest request,
      StringBuilder stringBuilder,
      String suffix) {
    frameSecondPartForUnhideAndHide(request, stringBuilder, suffix);
    partForSubSection(request, stringBuilder, suffix);
    return stringBuilder;
  }

  private StringBuilder frameCommonPartForUnhideAndHide(
      RuleRequest request,
      StringBuilder stringBuilder,
      String suffix) {
    List<String> targetQuestionIdentifiers = request.targetIdentifiers();
    if ("Question".equalsIgnoreCase(request.targetType().toString())) {
      for (String targetId : targetQuestionIdentifiers) {
        targetId += suffix;
        if (Objects.equals(request.ruleFunction(), UNHIDE) && Objects.equals(request.comparator().getValue(), "=")) {
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

  private StringBuilder frameSecondPartForUnhideAndHide(RuleRequest request,
      StringBuilder stringBuilder, String suffix) {
    frameCommonPartForUnhideAndHide(request, stringBuilder, suffix);
    stringBuilder.append(" }");
    return stringBuilder;
  }

  private StringBuilder frameSubSectionPartForUnhideAndHide(RuleRequest request,
      StringBuilder stringBuilder, String suffix) {
    return nestedSubSection(request, stringBuilder, suffix);
  }

  private StringBuilder nestedSubSection(
      RuleRequest request,
      StringBuilder stringBuilder,
      String suffix) {
    List<String> targetQuestionIdentifiers = request.targetIdentifiers();
    if ("Subsection".equalsIgnoreCase(request.targetType().toString())) {
      for (String targetId : targetQuestionIdentifiers) {
        targetId += suffix;
        if (Objects.equals(request.ruleFunction(), UNHIDE) && Objects.equals(request.comparator().getValue(), "=")) {
          stringBuilder.append("pgSubSectionShown('").append(targetId).append(PARANTHESIS);
        } else {
          stringBuilder.append("pgSubSectionHidden('").append(targetId).append(PARANTHESIS);
        }
        if (!Objects.equals(request.ruleFunction().toString(), UNHIDE)
            && Objects.equals(request.comparator().getValue(),
                "=")) {
          stringBuilder.append("pgSubSectionHidden('").append(targetId).append(PARANTHESIS);
        } else {
          stringBuilder.append("pgSubSectionShown('").append(targetId).append(PARANTHESIS);
        }
      }
    }
    return stringBuilder;
  }

  private StringBuilder partForSubSection(RuleRequest request, StringBuilder stringBuilder,
      String suffix) {
    stringBuilder = frameSubSectionPartForUnhideAndHide(request, stringBuilder, suffix);
    return stringBuilder;
  }


  public Rule updatePageRule(Long ruleId, RuleRequest request, Long userId, Long page) {
    boolean isPresent = waRuleMetaDataRepository.existsById(ruleId);
    if (!isPresent) {
      return null; // TODO
    } else {
      WaRuleMetadata waRuleMetadata = waRuleMetaDataRepository.getReferenceById(ruleId);
      RuleData ruleData = createRuleData(request, waRuleMetadata);
      WaRuleMetadata updatedValues = setUpdatedValues(ruleData, waRuleMetadata, request, userId, page);
      waRuleMetaDataRepository.save(updatedValues);
      return null;
    }
  }

  private WaRuleMetadata setUpdatedValues(
      RuleData ruleData,
      WaRuleMetadata ruleMetadata,
      RuleRequest request,
      Long userId, Long page) {
    Instant now = Instant.now();
    ruleMetadata.setRuleCd(request.ruleFunction().getValue());
    ruleMetadata.setLogic(request.comparator().getValue());
    ruleMetadata.setJsFunction(ruleData.jsFunctionNameHelper().jsFunction());
    ruleMetadata.setSourceValues(ruleData.sourceValues());
    ruleMetadata.setErrormsgText(ruleData.errorMsgText());
    ruleMetadata.setSourceQuestionIdentifier(ruleData.sourceIdentifier());
    ruleMetadata.setTargetQuestionIdentifier(ruleData.targetIdentifiers());
    ruleMetadata.setTargetType(request.targetType().toString());
    ruleMetadata.setAddTime(now);
    ruleMetadata.setAddUserId(userId);
    ruleMetadata.setLastChgTime(now);
    ruleMetadata.setRecordStatusCd("ACTIVE");
    ruleMetadata.setLastChgUserId(userId);
    ruleMetadata.setRecordStatusTime(now);
    ruleMetadata.setErrormsgText(ruleData.errorMsgText());
    ruleMetadata.setJsFunction(ruleData.jsFunctionNameHelper().jsFunction());
    ruleMetadata.setJsFunctionName(ruleData.jsFunctionNameHelper().jsFunctionName());
    ruleMetadata.setWaTemplateUid(page);
    ruleMetadata.setRuleExpression(ruleData.ruleExpression());
    ruleMetadata.setId(ruleMetadata.getId());
    ruleMetadata.setRuleDescText(request.description());

    return ruleMetadata;
  }
}
