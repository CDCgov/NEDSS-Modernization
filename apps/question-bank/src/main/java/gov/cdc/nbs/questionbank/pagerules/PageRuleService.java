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

    if (REQUIRE_IF.equals(request.ruleFunction().getValue())) {
      expressionValues = requireIfFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
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
