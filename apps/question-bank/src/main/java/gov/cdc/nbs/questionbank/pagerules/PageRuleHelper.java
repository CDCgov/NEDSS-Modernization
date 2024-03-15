package gov.cdc.nbs.questionbank.pagerules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;

@Component
public class PageRuleHelper {
  private static final String ELSE = " } else { \n";
  private static final String IF = "\n if(";
  private static final String ARRAY = "($j.inArray('";
  private static final String PARANTHESIS = "');\n";
  private static final String DOLLARCLOSING = "$j('#";
  private static final String PG_ENABLE_ELEMENT = "pgEnableElement('";
  private static final String PG_DISABLE_ELEMENT = "pgDisableElement('";
  private static final String SELECTED = " :selected').each(function(i, selected){";
  private static final String LINESEPERATOR_PARANTHESIS = " });\n";
  private static final String FUNCTION = "function ";
  private static final String ACTION_1 = "()\n{";
  private static final String DATE_COMPARE = "Date Compare";
  private static final String DISABLE = "Disable";
  private static final String ENABLE = "Enable";
  private static final String REQUIRE_IF = "Require If";
  private static final String HIDE = "Hide";
  private static final String UNHIDE = "Unhide";
  private static final String MUST_BE = "must be";
  private static final String ANY_SOURCE_VALUE = "( Any Source Value )";
  private static final String FOO_STRING = "',foo) > -1)";


  private final ConceptFinder finder;

  public PageRuleHelper(final ConceptFinder finder) {
    this.finder = finder;
  }


  public RuleData createRuleData(
      RuleRequest request,
      long ruleMetadata,
      long page) {
    SourceValuesHelper sourceValuesHelper = sourceValuesHelper(request, page);
    TargetValuesHelper targetValuesHelper = targetValuesHelper(request);
    RuleExpressionHelper expressionValues = null;


    if (HIDE.equals(request.ruleFunction().getValue())) {
      expressionValues = hideFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
    }
    if (REQUIRE_IF.equals(request.ruleFunction().getValue())) {
      expressionValues = requireIfFunction(request, sourceValuesHelper, targetValuesHelper,
          ruleMetadata);
    }
    if (UNHIDE.equals(request.ruleFunction().getValue())) {
      expressionValues =
          unHideFunction(request, sourceValuesHelper, targetValuesHelper, ruleMetadata);
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

  private RuleExpressionHelper unHideFunction(
      RuleRequest request,
      SourceValuesHelper sourceValuesHelper,
      TargetValuesHelper targetValuesHelper,
      long ruleMetadata) {
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
    JSFunctionNameHelper jsFunctionNameHelper =
        jsForHideAndUnhide(request, sourceValuesHelper, ruleMetadata);
    return new RuleExpressionHelper(errorMessageText, ruleExpression,
        new JSFunctionNameHelper(jsFunctionNameHelper.jsFunction(),
            jsFunctionNameHelper.jsFunctionName()));
  }

  private SourceValuesHelper sourceValuesHelper(RuleRequest request, long page) {
    String sourceText = request.sourceText();
    String sourceIdentifier = request.sourceIdentifier();
    List<Rule.SourceValue> sourceValues = request.sourceValues();
    String sourceIds = null;
    String sourceValueText = null;
    if (request.anySourceValue()) {
      // find all concepts for question's valueset
      List<Concept> concepts = finder.findByQuestionIdentifier(sourceIdentifier, page);
      sourceIds = concepts.stream().map(Concept::localCode).collect(Collectors.joining(","));
      sourceValueText = concepts.stream().map(Concept::display).collect(Collectors.joining(","));
    } else if (sourceValues != null) {
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



  private RuleExpressionHelper requireIfFunction(
      RuleRequest request,
      SourceValuesHelper sourceValuesHelper,
      TargetValuesHelper targetValuesHelper,
      long ruleMetadata) {
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
          .concat("( " + targetIdentifier + " )");
      for (String targetText : targetTextList) {
        String errMsg = sourceText.concat(" ")
            .concat(request.comparator().getValue())
            .concat(" ")
            .concat("( " + sourceValueText + " )")
            .concat(" ")
            .concat(targetText)
            .concat(" ")
            .concat("is required");
        errorMessageList.add(errMsg);
      }
    }
    String errorMessageText = String.join(",", errorMessageList);
    JSFunctionNameHelper jsFunctionNameHelper =
        requireIfJsFunction(request, sourceValuesHelper, ruleMetadata,
            targetValuesHelper);
    return new RuleExpressionHelper(errorMessageText, ruleExpression,
        new JSFunctionNameHelper(jsFunctionNameHelper.jsFunction(),
            jsFunctionNameHelper.jsFunctionName()));
  }

  private RuleExpressionHelper hideFunction(
      RuleRequest request,
      SourceValuesHelper sourceValuesHelper,
      TargetValuesHelper targetValuesHelper,
      long ruleMetadata) {
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

  public JSFunctionNameHelper requireIfJsFunction(
      RuleRequest request,
      SourceValuesHelper sourceValuesHelper,
      long ruleMetadata,
      TargetValuesHelper targetValuesHelper) {
    String functionName = "ruleRequireIf" + request.sourceIdentifier() + ruleMetadata;
    StringBuilder buffer = new StringBuilder();
    buffer.append(FUNCTION + functionName + ACTION_1);
    buffer.append("\n var foo = [];\n");
    buffer.append(DOLLARCLOSING).append(request.sourceIdentifier()).append(SELECTED);
    buffer.append("\n foo[i] = $j(selected).val();\n");
    buffer.append(LINESEPERATOR_PARANTHESIS);
    if (request.sourceIdentifier() != null) {
      if (request.anySourceValue()) {
        buffer.append("if(foo.length>0 && foo[0] != '') {\n");
      } else {
        List<String> sourceIDs = Arrays.asList(sourceValuesHelper.sourceValueIds().split(","));
        String sourceValueID = sourceValuesHelper.sourceValueIds();
        buffer.append("if(foo.length==0) return;\n");
        buffer.append(IF);
        if (sourceIDs.size() > 1) {
          buffer.append(ARRAY).append(sourceIDs.get(0)).append(FOO_STRING);
          for (int counter = 1; counter < sourceIDs.size(); counter++) {
            buffer.append(" || ");
            buffer.append(ARRAY).append(sourceIDs.get(counter)).append(FOO_STRING);
          }
        } else {
          buffer.append(ARRAY).append(sourceValueID).append(FOO_STRING);

        }
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
      long ruleMetadata) {
    String sourceText = sourceValuesHelper.sourceValueText();
    if (sourceText == null) {
      sourceText = ",";
    }
    List<String> sourceValueTextList = Arrays.asList(sourceText.split(","));
    StringBuilder stringBuilder = new StringBuilder();
    String functionName = "ruleHideUnh" + request.sourceIdentifier() + ruleMetadata;
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

  private StringBuilder frameSecondPartForUnhideAndHide(RuleRequest request,
      StringBuilder stringBuilder, String suffix) {
    frameCommonPartForUnhideAndHide(request, stringBuilder, suffix);
    stringBuilder.append(" }");
    return stringBuilder;
  }

  private StringBuilder partForSubSection(RuleRequest request, StringBuilder stringBuilder,
      String suffix) {
    stringBuilder = frameSubSectionPartForUnhideAndHide(request, stringBuilder, suffix);
    return stringBuilder;
  }

  private StringBuilder frameSubSectionPartForUnhideAndHide(RuleRequest request,
      StringBuilder stringBuilder, String suffix) {
    return nestedSubSection(request, stringBuilder, suffix);
  }

  private StringBuilder frameCommonPartForUnhideAndHide(
      RuleRequest request,
      StringBuilder stringBuilder,
      String suffix) {
    List<String> targetQuestionIdentifiers = request.targetIdentifiers();
    if ("Question".equalsIgnoreCase(request.targetType().toString())) {
      for (String targetId : targetQuestionIdentifiers) {
        targetId += suffix;
        if (Objects.equals(request.ruleFunction().getValue(), UNHIDE) && Objects.equals(request.comparator().getValue(),
            "=")) {
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

  private StringBuilder nestedSubSection(
      RuleRequest request,
      StringBuilder stringBuilder,
      String suffix) {
    List<String> targetQuestionIdentifiers = request.targetIdentifiers();
    if ("Subsection".equalsIgnoreCase(request.targetType().toString())) {
      for (String targetId : targetQuestionIdentifiers) {
        targetId += suffix;
        if (Objects.equals(request.ruleFunction().getValue(), UNHIDE) && Objects.equals(request.comparator().getValue(),
            "=")) {
          stringBuilder.append("pgSubSectionShown('").append(targetId).append(PARANTHESIS);
        } else {
          stringBuilder.append("pgSubSectionHidden('").append(targetId).append(PARANTHESIS);
        }
        if (!Objects.equals(request.ruleFunction().getValue(), UNHIDE)
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
}
