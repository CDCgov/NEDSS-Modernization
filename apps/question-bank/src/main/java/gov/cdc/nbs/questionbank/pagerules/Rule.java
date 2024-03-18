package gov.cdc.nbs.questionbank.pagerules;

import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
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

    public static RuleFunction valueFrom(String value) {
      return Arrays.stream(Rule.RuleFunction.values())
          .filter(f -> f.getValue().equalsIgnoreCase(value))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Failed to find RuleFunction for value: " + value));
    }

    public static RuleFunction valueFromNullable(String value) {
      if (value == null) {
        return null;
      }
      return Arrays.stream(Rule.RuleFunction.values())
          .filter(f -> f.getValue().equalsIgnoreCase(value))
          .findFirst()
          .orElse(null);
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

    public static Comparator valueFrom(String value) {
      return Arrays.stream(Rule.Comparator.values())
          .filter(f -> f.getValue().equalsIgnoreCase(value))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Failed to find Comparator for value: " + value));
    }

    public static Comparator valueFromNullable(String value) {
      if (value == null) {
        return null;
      }
      return Arrays.stream(Rule.Comparator.values())
          .filter(f -> f.getValue().equalsIgnoreCase(value))
          .findFirst()
          .orElse(null);
    }
  }


  public enum TargetType {
    QUESTION,
    SUBSECTION
  }


}


