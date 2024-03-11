package gov.cdc.nbs.questionbank.pagerules;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public record Rule(
    @ApiModelProperty(required = true) long id,
    @ApiModelProperty(required = true) long template,
    @ApiModelProperty(required = true) RuleFunction ruleFunction,
    String description,
    @ApiModelProperty(required = true) SourceQuestion sourceQuestion,
    @ApiModelProperty(required = true) boolean anySourceValue,
    List<String> sourceValues,
    @ApiModelProperty(required = true) Comparator comparator,
    @ApiModelProperty(required = true) TargetType targetType,
    @ApiModelProperty(required = true) List<Target> targets) {

  public record CreateRuleRequest(
      @ApiModelProperty(required = true) RuleFunction ruleFunction,
      String description,
      @ApiModelProperty(required = true) String sourceIdentifier,
      @ApiModelProperty(required = true) boolean anySourceValue,
      List<SourceValue> sourceValues,
      @ApiModelProperty(required = true) Comparator comparator,
      @ApiModelProperty(required = true) TargetType targetType,
      @ApiModelProperty(required = true) List<String> targetIdentifiers,
      String sourceText,
      List<String> targetValueText) {
  }


  public record SourceValue(String id, String text) {
  }


  public record SourceQuestion(String questionIdentifier, String label, String codeSetName) {
  }


  public record Target(String targetIdentifier, String label) {
  }


  public enum RuleFunction {
    DATE_COMPARE("Date Compare"),
    DISABLE("Disable"),
    ENABLE("Enable"),
    HIDE("Hide"),
    REQUIRE_IF("Require If"),
    UNHIDE("Unhide");

    private final String value;

    RuleFunction(String value) {
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  }


  public enum Comparator {
    EQUAL_TO("="),
    NOT_EQUAL_TO("<>"),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL_TO(">="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL_TO("<=");

    private final String value;

    Comparator(String value) {
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  }


  public enum TargetType {
    QUESTION,
    SUBSECTION
  }


}


