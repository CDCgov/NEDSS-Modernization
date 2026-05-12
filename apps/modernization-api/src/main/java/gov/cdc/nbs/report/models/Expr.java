package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Expr.RuleGroup.class, name = "RuleGroup"),
  @JsonSubTypes.Type(value = Expr.Rule.class, name = "Rule")
})
public sealed interface Expr {
  // Subset of https://react-querybuilder.js.org/docs/typescript#rules-and-groups

  record RuleGroup(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String id, // uuid
      @Schema(
              requiredMode = Schema.RequiredMode.REQUIRED,
              allowableValues = {"or", "and"})
          @NotNull String combinator,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull List<Expr> rules)
      implements Expr {}

  record Rule(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String id, // uuid
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String field, // column UID stringified
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String operator,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String value)
      implements Expr {}
}
