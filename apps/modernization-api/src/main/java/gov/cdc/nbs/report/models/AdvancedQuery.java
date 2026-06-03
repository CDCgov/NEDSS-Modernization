package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(value = AdvancedQuery.RuleGroup.class, name = "RuleGroup"),
  @JsonSubTypes.Type(value = AdvancedQuery.Rule.class, name = "Rule")
})
public sealed interface AdvancedQuery {
  // Subset of https://react-querybuilder.js.org/docs/typescript#rules-and-groups

  record RuleGroup(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String id, // uuid
      @Schema(
              requiredMode = Schema.RequiredMode.REQUIRED)
          @NotNull ReportConstants.QueryCombinators combinator,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull List<AdvancedQuery> rules)
      implements AdvancedQuery {}

  record Rule(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String id, // uuid
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Long columnId,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String operator,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String value)
      implements AdvancedQuery {}
}
