package gov.cdc.nbs.questionbank.pagerules;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record Rule(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long template,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) RuleFunction ruleFunction,
    String description,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) SourceQuestion sourceQuestion,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean anySourceValue,
    List<String> sourceValues,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Comparator comparator,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) TargetType targetType,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<Target> targets) {

  public record SourceValue(String id, String text) {}

  public record SourceQuestion(String questionIdentifier, String label, String codeSetName) {}

  public record Target(String targetIdentifier, String label) {}

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
